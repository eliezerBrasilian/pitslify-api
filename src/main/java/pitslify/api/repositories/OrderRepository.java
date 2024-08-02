package pitslify.api.repositories;

import pitslify.api.entities.OrderEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<OrderEntity, String> {
//    List<PaymentRequest> findByUserId(String compradorId);
//    List<PaymentRequest> findByUserIdOrderByCreatedAtDesc(String userId);
}
