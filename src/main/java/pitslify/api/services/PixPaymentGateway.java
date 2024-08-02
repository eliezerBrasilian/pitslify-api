package pitslify.api.services;

import pitslify.api.controllers.PaymentController;
import pitslify.api.dtos.MercadoPagoNotificacaoRequestDto;
import pitslify.api.dtos.OrderRequestBodyDto;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface PixPaymentGateway {

    ResponseEntity<Object> generatePixKey(OrderRequestBodyDto orderRequestBodyDto);

    ResponseEntity<Object> processPayment(
            MercadoPagoNotificacaoRequestDto mercadoPagoNotificacaoRequestDto)
            throws MPException, MPApiException;

    ResponseEntity<Map<String, String>> generatePixKeyFake(OrderRequestBodyDto orderRequestBodyDto);

    ResponseEntity<Object> checkPaymentStatusFake(String paymentID);

    ResponseEntity<Object> doPixPaymentFake(PaymentController.PaymentPixRequestDto paymentPixRequestDto);


}

