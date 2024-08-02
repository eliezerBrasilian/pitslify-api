package pitslify.api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pitslify.api.dtos.AppRequestDto;
import pitslify.api.entities.AppEntity;
import pitslify.api.records.FileDocument;
import pitslify.api.repositories.AppRepository;
import pitslify.api.services.AppService;

import java.util.Map;

@Service
public class AppServiceImpl implements AppService {
    @Autowired
    AppRepository appRepository;

    @Override
    public ResponseEntity<Object> createApp(AppRequestDto appRequestDto) {

        var appEntity = appRepository.save(new AppEntity(appRequestDto));
        return ResponseEntity.ok().body(
                Map.of(
                        "id",appEntity.getId(),
                        "message","App created successfully"
                )
        );
    }

    @Override
    public ResponseEntity<Object> addIcon(FileDocument fileDocument,String appId) {
        var appEntityOptional = appRepository.findById(appId);

        if(appEntityOptional.isEmpty()){
           throw new RuntimeException("Este app com o id " + appId + " não existe!");
        }
        var appEntity = appEntityOptional.get();
        appEntity.setIcon(fileDocument);

        appRepository.save(appEntity);
        return ResponseEntity.ok().body(
                Map.of("message","Icon added successfully")
        );
    }

    @Override
    public ResponseEntity<Object> addImages(FileDocument fileDocument, String appId) {
        var appEntityOptional = appRepository.findById(appId);

        if(appEntityOptional.isEmpty()){
            throw new RuntimeException("Este app com o id " + appId + " não existe!");
        }
        var appEntity = appEntityOptional.get();
        appEntity.setIcon(fileDocument);

        appRepository.save(appEntity);
        return ResponseEntity.ok().body(
                Map.of("message","Icon added successfully")
        );
    }

    @Override
    public ResponseEntity<Object> addAab(FileDocument fileDocument, String appId) {
        var appEntityOptional = appRepository.findById(appId);

        if(appEntityOptional.isEmpty()){
            throw new RuntimeException("Este app com o id " + appId + " não existe!");
        }
        var appEntity = appEntityOptional.get();
        appEntity.setIcon(fileDocument);

        appRepository.save(appEntity);
        return ResponseEntity.ok().body(
                Map.of("message","Icon added successfully")
        );
    }
}
