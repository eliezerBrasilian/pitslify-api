package pitslify.api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public record MercadoPagoNotificacaoRequestDto(
        @JsonProperty("id") long id,
        @JsonProperty("live_mode") boolean liveMode,
        @JsonProperty("type") String type,
        @JsonProperty("date_created") OffsetDateTime dateCreated,
        @JsonProperty("user_id") long userId,
        @JsonProperty("api_version") String apiVersion,
        @JsonProperty("action") String action,
        @JsonProperty("data") DataDto data,
        @JsonProperty("content") String content
) {
    public record DataDto(
            @JsonProperty("id") String id
    ) {}
}