package pitslify.api.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pitslify.api.dtos.AppRequestDto;
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

    private String appStatus;
    private long createdAt;


    public AppEntity(AppRequestDto appRequestDto){
        this.name = appRequestDto.data().name();
        this.shortDescription =  appRequestDto.data().shortDescription();
        this.longDescription =  appRequestDto.data().longDescription();
        this.hasAdds =  appRequestDto.data().hasAdds();
        this.collectsLocalization =  appRequestDto.data().collectLocalization();
        this.loginData = appRequestDto.data().loginData();
        this.allowsPurchase = appRequestDto.data().allowsPurchase();

        this.images = new ArrayList<>();
        this.appStatus = appRequestDto.status().value;
        this.createdAt = System.currentTimeMillis();

    }
}
