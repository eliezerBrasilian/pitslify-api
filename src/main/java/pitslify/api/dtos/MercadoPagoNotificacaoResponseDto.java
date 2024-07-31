package pitslify.api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MercadoPagoNotificacaoResponseDto(
        @JsonProperty("content") String content
) {

}