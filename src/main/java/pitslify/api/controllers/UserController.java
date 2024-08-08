package pitslify.api.controllers;


import pitslify.api.dtos.AppResponseDto;
import pitslify.api.enums.TransferStatus;
import pitslify.api.enums.UpdateStatus;
import pitslify.api.records.Address;
import pitslify.api.repositories.AppRepository;
import pitslify.api.repositories.UserRepository;
import pitslify.api.services.UserService;
import pitslify.api.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pitslify.api.dtos.ProfilePhotoDto;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Map;

@RestController
@RequestMapping(AppUtils.baseUrl + "/user")

public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AppRepository appRepository;

    @PostMapping("update-photo")
    ResponseEntity<Object> updatePhoto(@RequestBody ProfilePhotoDto profilePhotoDto) {
        return userService.updatePhoto(profilePhotoDto);
    }

    @PostMapping("address/update/{userId}")
    ResponseEntity<Object> updateAddress(@RequestBody Address address, @PathVariable String userId) {
        System.out.println("address");
        System.out.println(address);
        System.out.println(userId);
        return userService.updateAddress(address, userId);
    }

    @GetMapping("check-send-app/{id}")
    ResponseEntity<Object> checkIfUserCanSendApp(@PathVariable String id) {

        var userEntity = userRepository.findById(id).orElseThrow(()->new RuntimeException(""));

        try{
            return ResponseEntity.ok().body(
                    Map.of("message","sucesso",
                            "has_permission",userEntity.getCanSendApp())
            );
        }catch (RuntimeException e){
            userEntity.setCanSendApp(false);
            userRepository.save(userEntity);
            return ResponseEntity.ok().body(
                    Map.of("message","sucesso",
                            "has_permission",userEntity.getCanSendApp())
            );
        }
    }

    @PostMapping("update-send-app/{id}")
    ResponseEntity<Object> updatePermissionAboutSendApp(@PathVariable String id){

        var userEntity = userRepository.findById(id).orElseThrow(()->new RuntimeException(""));
        userEntity.setCanSendApp(false);
        userRepository.save(userEntity);

        try{
            return ResponseEntity.ok().body(
                    Map.of("message","permissao atualizada",
                            "has_permission",userEntity.getCanSendApp())
            );
        }catch (RuntimeException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("my-apps/{userId}")
    public ArrayList<AppResponseDto> getAllApps(@PathVariable String userId){
        var userEntity = userRepository.findById(userId).orElseThrow(()->new RuntimeException("user com esse id não foi encontrado"));

         var apps = appRepository.findByUserId(userId);

         var appsResponse = new ArrayList<AppResponseDto>();

         apps.forEach(i-> {

             String iconBase64 = i.getIcon()!= null? Base64.getEncoder().encodeToString(i.getIcon().data()) : "";

             appsResponse.add(new AppResponseDto(
                     i.getId(),
                     i.getUserId(),
                     i.getName(),
                     i.getShortDescription(),
                     i.getLongDescription(),
                     i.getHasAdds(),
                     i.getCollectsLocalization(),
                     i.getLoginData(),
                     i.getAllowsPurchase(),
                     iconBase64,
                     i.getImages(),
                     i.getAab(),
                     i.getAppStatus(),
                     i.getCreatedAt(),
                     i.getGooglePlayLink(),
                     i.getTransferStatus(),
                     i.getUpdateStatus())
             );
         });

         return appsResponse;
    }

    @PostMapping("request-app-transfer/{userId}/{appId}")
    ResponseEntity<Object> requestAppTransfer(@PathVariable String userId,
                                              @PathVariable String appId){

        userRepository.findById(userId).orElseThrow(()->new RuntimeException("user com esse id não foi encontrado"));
        var appEntity = appRepository.findById(appId).orElseThrow(()->new RuntimeException("app com esse id não foi encontrado"));

        appEntity.setTransferStatus(TransferStatus.REQUESTED);

        appRepository.save(appEntity);
        try{
            return ResponseEntity.ok().body(
                    Map.of("message","transferência solicitada com sucesso")
            );
        }catch (RuntimeException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @PostMapping("allow-post-app/{userId}")
    public ResponseEntity<Object> allowUserSendApp(@PathVariable String userId){
        var userEntity = userRepository.findById(userId).orElseThrow(()->new RuntimeException("user com esse id não foi encontrado"));

        userEntity.setCanSendApp(true);

        userRepository.save(userEntity);

        return ResponseEntity.ok().body(
                Map.of("message","permissao concedida com sucesso")
        );
    }

    @PostMapping("request-app-update/{userId}/{appId}")
    ResponseEntity<Object> requestAppUpdate(@PathVariable String userId,
                                              @PathVariable String appId){

        userRepository.findById(userId).orElseThrow(()->new RuntimeException("user com esse id não foi encontrado"));
        var appEntity = appRepository.findById(appId).orElseThrow(()->new RuntimeException("app com esse id não foi encontrado"));

        appEntity.setUpdateStatus(UpdateStatus.REQUESTED);

        appRepository.save(appEntity);

        return ResponseEntity.ok().body(
                    Map.of("message","atualização solicitada com sucesso")
        );
    }
}
