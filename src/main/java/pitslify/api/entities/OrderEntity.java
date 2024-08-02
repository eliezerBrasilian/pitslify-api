package pitslify.api.entities;

import pitslify.api.enums.OrderType;
import pitslify.api.enums.Platform;
import pitslify.api.dtos.OrderRequestBodyDto;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Document(collection = "orders")
public class OrderEntity {
    @Id
    private String id;
    private Platform platform;
    private String status;
    private OrderRequestBodyDto.ProductData product;
    private OrderRequestBodyDto.UserData payer;
    private long createdAt;
    private OrderType orderType;

    public OrderEntity(OrderRequestBodyDto orderRequestBodyDto){
        this.platform = orderRequestBodyDto.platform();
        this.status = "waiting";
        this.product = orderRequestBodyDto.productData();
        this.payer = orderRequestBodyDto.userData();
        this.createdAt = System.currentTimeMillis();
        this.orderType = orderRequestBodyDto.type();
    }
}
