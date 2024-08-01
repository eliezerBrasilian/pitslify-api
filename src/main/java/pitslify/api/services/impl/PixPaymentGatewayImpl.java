package pitslify.api.services.impl;

import org.springframework.beans.factory.annotation.Value;
import pitslify.api.controllers.PaymentController;
import pitslify.api.dtos.AuthRequestDto;
import pitslify.api.dtos.MercadoPagoNotificacaoRequestDto;
import pitslify.api.dtos.MercadoPagoNotificacaoResponseDto;
import pitslify.api.enums.UserRole;
import pitslify.api.models.OrderEntity;
import pitslify.api.records.OrderRequestBodyDto;
import pitslify.api.repositories.OrderRepository;
import pitslify.api.services.MercadoPagoApiService;
import pitslify.api.services.PixPaymentGateway;
import pitslify.api.utils.AppUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

@Service
public class PixPaymentGatewayImpl implements PixPaymentGateway {
    private final Logger logger = Logger.getLogger(PixPaymentGatewayImpl.class.getName());

    @Value("${prod.access.token}")
    private String prodAccessToken;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    AuthService authService;

    @Autowired
    MercadoPagoApiService mercadoPagoApiService;

    @Override
    public ResponseEntity<Object> generatePixKey(OrderRequestBodyDto orderRequestBodyDto){
        logger.info("ProdAccesToken: " + prodAccessToken);
        MercadoPagoConfig.setAccessToken(prodAccessToken);

        var requestOptions = mercadoPagoApiService.getRequestOptions();

        var client = new PaymentClient();

        var orderEntity = orderRepository
                .save(new OrderEntity(orderRequestBodyDto));

        var orderId = orderEntity.getId();
        var paymentCreateRequest = mercadoPagoApiService.buildPaymentRequest(orderRequestBodyDto, orderId);

        try {
            var payment = client.create(paymentCreateRequest, requestOptions);
            System.out.println(payment);

            var qrcodeBase64 = payment.getPointOfInteraction().getTransactionData().getQrCodeBase64();
            var qrcode = payment.getPointOfInteraction().getTransactionData().getQrCode();

            var response = Map.of(
                    "message", "sucesso",
                    "qrcode", qrcode,
                    "qrcodeBase64", qrcodeBase64,
                    "order_id", orderId
            );
            System.out.println(response);

            return ResponseEntity.ok().body(response);

        }catch (MPApiException | MPException e){
            logger.info(e.getLocalizedMessage());
           throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> processPayment(
            MercadoPagoNotificacaoRequestDto mercadoPagoNotificacaoRequestDto)
            throws MPException, MPApiException {

        MercadoPagoConfig.setAccessToken(prodAccessToken);

        var pagamento = new PaymentClient();

        Long paymentId = Long.valueOf(mercadoPagoNotificacaoRequestDto.data().id());
        System.out.println("paymentID: " + paymentId);


        try{
            var paymentFounded = pagamento.get(paymentId);

            if(paymentFounded != null){
                System.out.println("status: "+paymentFounded.getStatus());

                String orderId = paymentFounded.getExternalReference();

                System.out.println("orderId: "+orderId);

                if(Objects.equals(paymentFounded.getStatus(), "approved")){
                    System.out.println("pagamento foi aprovado, agora vamos para implementação confirmaPagamento()");

                    var optionalOrderEntity = orderRepository.findById(orderId);

                    if(optionalOrderEntity.isEmpty()){
                         throw new RuntimeException("pagamento não existe");
                    }

                    var orderEntity = optionalOrderEntity.get();
                    orderEntity.setStatus("approved");

                    orderRepository.save(orderEntity);

                   // sendCustomWebhook(paymentRequestData);

                    var login  = createLogin(orderEntity.getPayer(),
                            orderEntity.getProduct());

                    return ResponseEntity.ok().body(
                            Map.of("message: ", "Pagamento aprovado com sucesso",
                                    "data",login
                            ));
                }

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("não existe um pagamento com esse id");
            }
            else {
                logger.info("não existe um pagamento com esse id");
                return ResponseEntity.ok().body("não existe um pagamento com esse id");
            }

        }catch (RuntimeException e){
            throw new RuntimeException("excessao ao buscar paymentId, devido a: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Map<String, String>> generatePixKeyFake(OrderRequestBodyDto orderRequestBodyDto) {

        try {

            var orderEntity = orderRepository
                    .save(new OrderEntity(orderRequestBodyDto));

            var orderId = orderEntity.getId();

           // sendCustomWebhook(paymentRequest);

            return ResponseEntity.ok().body(
                    Map.of(
                            "order_id",orderId,
                            "pix_key", "00020126420014br.gov.bcb.pix0120admin@foodfacil.site520400005303986540525.505802BR5923DEELIEZER202202110139296009Sao Paulo62240520mpqrinter791905408246304A712"
                    )

            ) ;
        } catch (Exception e) {
            // Logar a exceção detalhadamente
            logger.severe("Erro ao gerar a chave Pix fake: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro encontrado na tentativa de devolver a chave pix", e);
        }
    }

    private void sendCustomWebhook(OrderEntity orderEntity) throws IOException, InterruptedException {
        var notificationContent = new MercadoPagoNotificacaoRequestDto(
                20359978,
                true,
                "customer",
                AppUtils.getBrasiliaOffsetTime(),
                Long.parseLong(orderEntity.getPayer().userId()),
                "v1",
                "action_payment",
                new MercadoPagoNotificacaoRequestDto.DataDto(orderEntity.getId()),
                orderEntity.getStatus()
        );

        // Configurar o ObjectMapper com o módulo JavaTimeModule
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // Converta o conteúdo da notificação para JSON
        String notificationContentJson = objectMapper.writeValueAsString(notificationContent);

        // Criar o objeto que será enviado ao Discord
        var discordResponse = new MercadoPagoNotificacaoResponseDto(notificationContentJson);

        // Converta o objeto para JSON
        String json = objectMapper.writeValueAsString(discordResponse);

        // Log JSON gerado
        logger.info("JSON gerado: " + json);

        // Crie o cliente HTTP e a requisição POST
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://discord.com/api/webhooks/1267133591599710229/Dr1Y6mZ4Zkw22JhD_AIgzCq1gf2-GDGrxD92XwQyrAnRwxXdzU0BOVMLJbQXCTzsmTAJ"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        // Envie a requisição e obtenha a resposta
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Logar a resposta
        logger.info("Status code: " + response.statusCode());
        logger.info("Resposta: " + response.body());

        if (response.statusCode() == 200 || response.statusCode() == 204) {
            System.out.println("Requisição enviada com sucesso");
        } else {
            System.out.println("Falha ao enviar a requisição: " + response.body());
        }
    }


    public ResponseEntity<Object> doPixPaymentFake(PaymentController.PaymentPixRequestDto paymentPixRequestDto) {
        var optionalPaymentRequestEntity = orderRepository.findById(paymentPixRequestDto.orderId());

        if(optionalPaymentRequestEntity.isEmpty()){
            return ResponseEntity.badRequest().body("orderId não existe");
        }

        var paymentRequestData = optionalPaymentRequestEntity.get();
        paymentRequestData.setStatus("approved");

        orderRepository.save(paymentRequestData);

       // sendCustomWebhook(paymentRequestData);

        var login  = createLogin(paymentRequestData.getPayer(),
                paymentRequestData.getProduct());

        return ResponseEntity.ok().body(
                Map.of("message: ", "Pagamento aprovado com sucesso",
                       "data",login
                        )
        );
    }

    record LoginData(String email, String password){};
    private LoginData createLogin(
            OrderRequestBodyDto.UserData payer,
            OrderRequestBodyDto.ProductData product){

        var password = AppUtils.generatePassword();

        authService.register(new AuthRequestDto(
                        payer.email(),
                        password,
                        UserRole.USER,
                        payer.firstName(),
                        ""
                )
        );

        return new LoginData(payer.email(), password);
    }

    @Override
    public ResponseEntity<Object> checkPaymentStatusFake(
            String paymentId) {

        var optionalPaymentRequestEntity = orderRepository.findById(paymentId);

        if(optionalPaymentRequestEntity.isEmpty()){
            return ResponseEntity.badRequest().body("pagamento não existe");
        }

        System.out.println("paymentID: " + paymentId);

        try{
            var paymentRequestData = optionalPaymentRequestEntity.get();

            if(Objects.equals(paymentRequestData.getStatus(), "approved")){
                    System.out.println("pagamento foi aprovado, agora vamos para implementação confirmaPagamento()");
                    //pedidoServiceImpl.confirmaPagamento(pedidoId);

                    return ResponseEntity.ok().body("Pagamento aprovado com sucesso");
                }

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("não existe um pagamento com esse id");

        }catch (RuntimeException e){
            throw new RuntimeException("excessao ao buscar paymentId, devido a: " + e.getMessage());
        }
    }


}

