package pitslify.api.interfaces;

import pitslify.api.enums.PaymentError;

public interface OnErrorPaymentCallback {
    void run(PaymentError paymentError);
}