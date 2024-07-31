package pitslify.api.dtos;

import pitslify.api.enums.UserRole;
import jakarta.validation.constraints.NotNull;

public record AuthRequestDto(@NotNull String email,
                             @NotNull String password,
                             @NotNull UserRole role,
                             @NotNull String name,
                             String profilePicture) { }