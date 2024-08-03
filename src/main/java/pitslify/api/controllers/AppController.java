package pitslify.api.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pitslify.api.dtos.AppRequestDto;
import pitslify.api.dtos.UploadFileResponseDto;
import pitslify.api.entities.AppEntity;
import pitslify.api.records.FileDocument;
import pitslify.api.repositories.AppRepository;
import pitslify.api.repositories.UserRepository;
import pitslify.api.services.AppService;
import pitslify.api.services.FileStorageService;
import pitslify.api.utils.AppUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping(AppUtils.baseUrl + "/app")
public class AppController {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private AppService appService;

    @Autowired
    private AppRepository appRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity<Object> createApp(
            @Valid
            @RequestBody AppRequestDto appRequestDto){

        var userEntity = userRepository.findById(appRequestDto.userData().id()).orElseThrow(()->new RuntimeException("user doesn't exist"));
        if(userEntity.getIsNewClient()){
            //todo se o usuario tiver acabado de criar a conta prossiga com a criação
           // userEntity.setIsNewClient(false);
           // userRepository.save((userEntity));
            return appService.createApp(appRequestDto);
        }
        else{
            //todo senao, devolva a chave pix pra ele,
            //todo se o pagamento for aprovado, prossiga com a criação
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                    Map.of("message","this user can't not send app at this point")
            );
        }
    }

    //app/file/upload/aab/66ae3231d907675c372ae498
    @PostMapping("file/upload/aab/{id}")
    public ResponseEntity<Object> uploadAab(
            @PathVariable("id") String id,
            @RequestBody String url
    ){
        var appEntity = appRepository.findById(id).orElseThrow(()->new RuntimeException("app não encontrado"));
        appEntity.setAab(url);

        appRepository.save(appEntity);

        return ResponseEntity.ok().body(
                Map.of( "message","sucesso")
        );
    }

    @PostMapping("file/upload/{id}/{type}")
    public UploadFileResponseDto uploadFileResponse(
            @RequestParam("file") MultipartFile file,
            @PathVariable("id") String id,
            @PathVariable("type") String type
    ){
        var fileDocument = fileStorageService.storeFile(file,id,type);

        //definindo caminho de download
        var fileDownloadUri = ServletUriComponentsBuilder.
                fromCurrentRequestUri()
                .path("/api/file/v1/download/")
                .path(fileDocument.fileName())
                .toUriString();

        var optionalAppEntity = appRepository.findById(id);
        if(optionalAppEntity.isEmpty())throw new RuntimeException("doesn't exist a row with this id " + id);
        else{
            var appEntity = optionalAppEntity.get();

            if(Objects.equals(type, "icon")){
                appEntity.setIcon(fileDocument);
            }
            else{
                var images = appEntity.getImages();
                images.add(fileDocument);
                appEntity.setImages(images);
            }

            appRepository.save(appEntity);

            return new UploadFileResponseDto(
                    fileDocument.fileName(),
                    fileDownloadUri,
                    file.getContentType(),
                    file.getSize()
            );
        }
    }

    @GetMapping("file/download/{id}/{type}")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable String id,
            @PathVariable String type) {

        var appEntity = appRepository.findById(id).orElseThrow(()->new RuntimeException("arquivo não encontrado"));

        var fileName = "";
        var fileType = "";
        var fileData = new byte[0];

        if(Objects.equals(type, "icon")){
            fileName = appEntity.getIcon().fileName();
            fileType = appEntity.getIcon().fileType();
            fileData = appEntity.getIcon().data();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(new ByteArrayResource(fileData));
    }

    @PostMapping("file/upload/images/{id}")
    public List<UploadFileResponseDto> uploadFilesResponse(
            @RequestParam("images")MultipartFile[] images,
            @PathVariable("id") String id
    ){
        return Arrays.asList(images)
                .stream()
                .map(image -> uploadFileResponse(image,id,"image"))
                .collect(Collectors.toList());
    }

}
