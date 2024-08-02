package pitslify.api.repositories;

import pitslify.api.entities.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {
    Optional<UserEntity> findByEmail(String email);

    @Query("{'_id': ?0}")
    Optional<UserEntity> updateProfilePictureById(String id, String newPhoto);

}
