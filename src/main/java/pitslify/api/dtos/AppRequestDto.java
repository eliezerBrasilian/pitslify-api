package pitslify.api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import pitslify.api.records.AppStatus;

public record AppRequestDto(
        @NotNull Data data,
        @NotNull @JsonProperty("user_data") UserData userData,
        @NotNull AppStatus status) {
    public record Data(
            String name,
            @JsonProperty("short_description") String shortDescription,
            @JsonProperty("long_description") String longDescription,
            @JsonProperty("has_ads") Boolean hasAdds,
            @JsonProperty("collects_localization") Boolean collectLocalization,
            @JsonProperty("login_data") LoginData loginData,
            @JsonProperty("allows_purchase") Boolean allowsPurchase){
    }
    public record UserData(
            String id,
            String email
            ){ }
    public record LoginData(
            String login,
            String password
    ){}
}
