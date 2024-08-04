package pitslify.api.dtos;

import pitslify.api.enums.AppStatus;
import pitslify.api.enums.TransferStatus;
import pitslify.api.records.FileDocument;

import java.util.List;

public record AppResponseDto(
        String id,
        String userId,
        String name,
        String shortDescription,
        String longDescription,
        Boolean hasAdds,
        Boolean collectsLocalization,
        AppRequestDto.LoginData loginData,
        Boolean allowsPurchase,
        String icon,
        List<FileDocument>images,
        String aab,
        AppStatus appStatus,
        long createdAt,
        String googlePlayLink,
        TransferStatus transferStatus
) {
}
