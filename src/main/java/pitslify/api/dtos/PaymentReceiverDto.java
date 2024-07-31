package pitslify.api.dtos;

public record PaymentReceiverDto(
        String deviceToken,
        String userName,
        String userPhone) {
}
