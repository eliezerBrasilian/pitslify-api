package pitslify.api.controllers;

import pitslify.api.dtos.LoginAuthDTO;
import pitslify.api.services.impl.AuthService;
import pitslify.api.utils.AppUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = {"http://localhost:5173",
        "https://food-facil-painel-admin-8mcqkbvbs-eliezerbrasilians-projects.vercel.app",
        "https://food-facil-painel-admin.vercel.app",
                "https://foodfacil-website.vercel.app"})
@RestController
@RequestMapping(AppUtils.baseUrl + "/auth")

public class AuthController {

    @Autowired
    AuthService authorizationService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginAuthDTO authetinticationDto) {
        System.out.println(authetinticationDto);
        return authorizationService.login(authetinticationDto);
    }
}