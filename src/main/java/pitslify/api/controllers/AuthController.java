package pitslify.api.controllers;

import pitslify.api.dtos.AuthRequestDto;
import pitslify.api.dtos.LoginAuthRequestDto;
import pitslify.api.services.impl.AuthService;
import pitslify.api.utils.AppUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping(AppUtils.baseUrl + "/auth")

public class AuthController {

    @Autowired
    AuthService authorizationService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginAuthRequestDto authenticationDto) {
        System.out.println(authenticationDto);
        return authorizationService.login(authenticationDto);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(
            @Valid @RequestBody AuthRequestDto authRequestDto
            ){
        return authorizationService.register(authRequestDto);
    }
}