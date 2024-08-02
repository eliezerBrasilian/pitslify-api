package pitslify.api.services;

import org.springframework.http.ResponseEntity;
import pitslify.api.dtos.AppRequestDto;
import pitslify.api.records.FileDocument;

public interface AppService {
    ResponseEntity<Object> createApp(AppRequestDto appRequestDto);
    ResponseEntity<Object> addIcon(FileDocument fileDocument, String appId);
    ResponseEntity<Object> addImages(FileDocument fileDocument, String appId);
    ResponseEntity<Object> addAab(FileDocument fileDocument, String appId);
}
