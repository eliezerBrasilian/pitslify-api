package pitslify.api.controllers;


import pitslify.api.records.Address;
import pitslify.api.services.UserService;
import pitslify.api.utils.AppUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pitslify.api.dtos.ProfilePhotoDto;
import pitslify.api.dtos.TokenDoDispositivoRequestDto;

@RestController
@RequestMapping(AppUtils.baseUrl + "/user")

public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/token-dispositivo")
    public ResponseEntity<Object> salvaOuAtualizaToken(@Valid @RequestBody TokenDoDispositivoRequestDto tokenDoDispositivoRequestDto) {
        return userService.salvaOuAtualizaToken(tokenDoDispositivoRequestDto);
    }

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

//    @PostMapping("cupom/add")
//    ResponseEntity<Object> addCupom(@RequestBody UserCupomDto userCupom) {
//        System.out.println("cupom recebido");
//
//        return userService.addCupom(userCupom);
//    }

//    @PostMapping("cupom/use")
//    ResponseEntity<Object> useCupom(@RequestBody CupomToUpdateDto cupomToUpdateDto) {
//
//        return userService.usarCupom(cupomToUpdateDto);
//    }


//    @GetMapping("pedidos/{userId}")
//    ResponseEntity<Object> getPedidos(@PathVariable String userId) {
//        return userService.getPedidos(userId);
//    }

}
