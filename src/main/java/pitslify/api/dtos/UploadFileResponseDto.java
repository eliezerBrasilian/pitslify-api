package pitslify.api.dtos;

public record UploadFileResponseDto(
        String fileName,
        String fileDownloadUri,
        String fileType,
        long size
) {
}
