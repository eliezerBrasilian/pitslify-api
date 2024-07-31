package pitslify.api.repositories;

import pitslify.api.models.TokenDoDIspositivo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenDoDispositivoRepository extends MongoRepository<TokenDoDIspositivo, String> {
    Optional<TokenDoDIspositivo> findByUserId(String email);
}
