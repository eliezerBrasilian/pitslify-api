package pitslify.api.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import pitslify.api.dtos.MercadoPagoNotificacaoRequestDto;
import pitslify.api.records.OrderRequestBodyDto;
import pitslify.api.repositories.OrderRepository;
import pitslify.api.repositories.UserRepository;
import pitslify.api.services.PixPaymentGateway;
import pitslify.api.utils.AppUtils;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(AppUtils.baseUrl + "/payment")
public class PaymentController {

    @Autowired
    PixPaymentGateway pixPaymentGateway;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping("buy/access/basic")
    ResponseEntity<Object> generatePix(@Valid @RequestBody OrderRequestBodyDto orderRequestBodyDto) {
        System.out.println(orderRequestBodyDto);

        return  pixPaymentGateway.generatePixKey(orderRequestBodyDto);
    }

    @PostMapping("mercadopago/notificacao")
    ResponseEntity<Object> processPayment(@RequestBody MercadoPagoNotificacaoRequestDto mercadoPagoNotificacaoRequestDto)
            throws MPException, MPApiException {

        System.out.println("recebido");
        System.out.println(mercadoPagoNotificacaoRequestDto);

        return pixPaymentGateway.processPayment(mercadoPagoNotificacaoRequestDto);
    }

    //    @DeleteMapping("/tokens-salvos-de-celular/{id}")
    //    public ResponseEntity<Object> excluiTokenSalvo(@PathVariable String id){

    @GetMapping("/check-order/{id}/{email}")
    public ResponseEntity<Object> getOrderStatus(@PathVariable String id, @PathVariable String email){

        System.out.println("id: " + id);
        var optional = orderRepository.findById(id);
        var optionalUser = userRepository.findByEmail(email);

        if(optional.isEmpty()){
            throw new RuntimeException("order not found");
        }
        else if(optionalUser.isEmpty()){
            throw new RuntimeException("user with this email not found");
        }

        //devolver o status da ordem
        //e eventualmente a senha que foi gerada para o usuário

        var orderEntity = optional.get();
        var userEntity = optionalUser.get();

        return ResponseEntity.ok().body(
                Map.of(
                        "status",orderEntity.getStatus(),
                        "password", userEntity.getPasswordNotEncrypted(),
                        "userId", userEntity.getId()
                )
        );
    }

    /*
    ##############
    ##############
     */

    @PostMapping("generate-pix-fake")
    ResponseEntity<Map<String, String>> generatePixKeyFake(
            @RequestBody OrderRequestBodyDto orderRequestBodyDto) {
        System.out.println(orderRequestBodyDto);

        return pixPaymentGateway.generatePixKeyFake(orderRequestBodyDto);
    }

    public record PaymentPixRequestDto(@JsonProperty("order_id") String orderId){};
    @PostMapping("do-payment-pix-fake")
    ResponseEntity<Object> pagamentoPixFake(
            @RequestBody
            PaymentPixRequestDto paymentPixRequestDto) {


        return  pixPaymentGateway.doPixPaymentFake(paymentPixRequestDto);
    }

//    @PostMapping("mercadopago/notificacao/fake")
//    ResponseEntity<Object> notificacaoFake(
//            @RequestBody
//            PaymentPixRequestDto paymentPixRequestDto) throws MPException, MPApiException {
//
//        System.out.println("recebido");
//        System.out.println(paymentPixRequestDto.payment_id);
//
//        return pixPaymentGateway.checkPaymentStatusFake(paymentPixRequestDto.payment_id);
//    }
}
