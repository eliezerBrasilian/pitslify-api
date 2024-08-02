package pitslify.api.services.impl;

import pitslify.api.dtos.OrderRequestBodyDto;
import pitslify.api.services.MercadoPagoApiService;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.core.MPRequestOptions;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class MercadoPagoApiServiceImpl implements MercadoPagoApiService {
    @Override
    public PaymentCreateRequest buildPaymentRequest(OrderRequestBodyDto orderRequestBodyDto, String orderId) {
        return PaymentCreateRequest.builder()
                .transactionAmount(new BigDecimal(orderRequestBodyDto.productData().price()))
                .description(orderRequestBodyDto.productData().description())
                .paymentMethodId("pix")
                .payer(
                        PaymentPayerRequest.builder()
                                .email(orderRequestBodyDto.userData().email())
                                .firstName(orderRequestBodyDto.userData().firstName())
                                .build())
                .externalReference(orderId)
                .build();
    }

    @Override
    public MPRequestOptions getRequestOptions() {
        Map<String, String> customHeaders = new HashMap<>();
        customHeaders.put("x-idempotency-key", UUID.randomUUID().toString());

        return MPRequestOptions.builder()
                .customHeaders(customHeaders)
                .build();
    }
}
