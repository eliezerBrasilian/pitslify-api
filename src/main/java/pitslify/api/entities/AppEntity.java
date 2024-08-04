package pitslify.api.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pitslify.api.dtos.AppRequestDto;
import pitslify.api.enums.TransferStatus;
import pitslify.api.enums.AppStatus;
import pitslify.api.records.FileDocument;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Document(collection = "apps")
public class AppEntity {
    @Id
    private String id;
    private String userId;
    private String name;
    private String shortDescription;
    private String longDescription;
    private Boolean hasAdds;
    private Boolean collectsLocalization;
    private AppRequestDto.LoginData loginData;
    private Boolean allowsPurchase;

    private FileDocument icon;
    private List<FileDocument> images;
    private String aab;

    private AppStatus appStatus;
    private long createdAt;

    private String googlePlayLink;

    private TransferStatus transferStatus;

    public AppEntity(AppRequestDto appRequestDto){
        this.userId = appRequestDto.userData().id();
        this.name = appRequestDto.data().name();
        this.shortDescription =  appRequestDto.data().shortDescription();
        this.longDescription =  appRequestDto.data().longDescription();
        this.hasAdds =  appRequestDto.data().hasAdds();
        this.collectsLocalization =  appRequestDto.data().collectLocalization();
        this.loginData = appRequestDto.data().loginData();
        this.allowsPurchase = appRequestDto.data().allowsPurchase();

        this.images = new ArrayList<>();
        this.appStatus = appRequestDto.status();
        this.googlePlayLink = "";
        this.transferStatus = TransferStatus.IDLE;
        this.createdAt = System.currentTimeMillis();
    }
}
