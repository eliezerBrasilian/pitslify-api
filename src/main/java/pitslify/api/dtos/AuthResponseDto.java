package pitslify.api.dtos;

public record AuthResponseDto(   String token,
                                 String userId,
                                 String profilePicture,
                                 String name,
                                 String email,
                                 long createdAt) {

}

