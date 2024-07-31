package pitslify.api.repositories;

import pitslify.api.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);

    @Query("{'_id': ?0}")
    Optional<User> updateProfilePictureById(String id, String newPhoto);

}
