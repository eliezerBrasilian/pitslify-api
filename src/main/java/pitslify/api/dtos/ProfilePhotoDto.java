package pitslify.api.dtos;

import jakarta.validation.constraints.NotNull;

public record ProfilePhotoDto (@NotNull String userUid,@NotNull String newProfilePhoto){ }
