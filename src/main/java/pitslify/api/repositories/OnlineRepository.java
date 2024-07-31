package pitslify.api.repositories;

import pitslify.api.models.Online;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface OnlineRepository extends MongoRepository<Online, String> {
}
