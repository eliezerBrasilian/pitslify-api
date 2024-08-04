package pitslify.api.controllers;


import pitslify.api.entities.AppEntity;
import pitslify.api.enums.TransferStatus;
import pitslify.api.records.Address;
import pitslify.api.repositories.AppRepository;
import pitslify.api.repositories.UserRepository;
import pitslify.api.services.UserService;
import pitslify.api.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pitslify.api.dtos.ProfilePhotoDto;

import java.util.List;
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
    public List<AppEntity> getAllApps(@PathVariable String userId){
        var userEntity = userRepository.findById(userId).orElseThrow(()->new RuntimeException("user com esse id não foi encontrado"));

        return appRepository.findByUserId(userId);
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
}
