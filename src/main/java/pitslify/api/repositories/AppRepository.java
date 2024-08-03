package pitslify.api.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;
import pitslify.api.entities.AppEntity;

import java.util.List;

public interface AppRepository extends MongoRepository<AppEntity, String> {
    List<AppEntity> findByUserId(String userId);
}
