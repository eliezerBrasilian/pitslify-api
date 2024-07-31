package pitslify.api.records;

import pitslify.api.enums.Platform;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record OrderRequestBodyDto(@NotNull @JsonProperty("user_data") UserData userData,
                                  @NotNull @JsonProperty("product_data") ProductData productData,
                                  Platform platform){

    public record UserData(String userId,
                           @NotNull String email,
                           @NotNull @JsonProperty("first_name")  String firstName)
    {}

    public record ProductData(
            @NotNull String title,
            @NotNull String description,
            @NotNull String price)
    {}

}