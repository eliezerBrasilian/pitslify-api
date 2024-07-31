package pitslify.api.models;

import pitslify.api.dtos.TokenDoDispositivoRequestDto;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class TokenDoDIspositivo {
    @Id
    private String id;
    private String token;
    private String userId;

    public TokenDoDIspositivo(TokenDoDispositivoRequestDto tokenDoDispositivoRequestDto){
        this.token = tokenDoDispositivoRequestDto.token();
        this.userId = tokenDoDispositivoRequestDto.userId();
    }
}
