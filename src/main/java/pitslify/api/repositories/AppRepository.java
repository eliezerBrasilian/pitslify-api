package pitslify.api.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;
import pitslify.api.entities.AppEntity;

public interface AppRepository extends MongoRepository<AppEntity, String> {
}
