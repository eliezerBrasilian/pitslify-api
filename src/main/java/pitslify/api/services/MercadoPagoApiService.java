package pitslify.api.services;

import pitslify.api.dtos.OrderRequestBodyDto;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.core.MPRequestOptions;

public interface MercadoPagoApiService {
    PaymentCreateRequest buildPaymentRequest(OrderRequestBodyDto orderRequestBodyDto, String orderId);

    MPRequestOptions getRequestOptions();
}
