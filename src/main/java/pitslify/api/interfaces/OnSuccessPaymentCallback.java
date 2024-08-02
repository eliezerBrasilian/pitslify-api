package pitslify.api.interfaces;

import pitslify.api.entities.OrderEntity;

public interface OnSuccessPaymentCallback {
    void run(OrderEntity orderEntity);
}