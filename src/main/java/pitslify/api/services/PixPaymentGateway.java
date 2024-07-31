package pitslify.api.services;

import pitslify.api.dtos.MercadoPagoNotificacaoRequestDto;
import pitslify.api.records.OrderRequestBodyDto;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import org.springframework.http.ResponseEntity;

public interface PixPaymentGateway {

    ResponseEntity<Object> generatePixKey(OrderRequestBodyDto orderRequestBodyDto);

    String generatePixKeyFake(OrderRequestBodyDto orderRequestBodyDto);

    ResponseEntity<Object> processPayment(MercadoPagoNotificacaoRequestDto mercadoPagoNotificacaoRequestDto) throws MPException, MPApiException;

    ResponseEntity<Object> checkPaymentStatusFake(String paymentID);

//    ResponseEntity<Object> doPixPaymentFake(
//            PaymentController.PaymentPixRequestDto paymentId) throws IOException, InterruptedException;
}

