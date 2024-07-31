package pitslify.api.controllers;


import pitslify.api.services.OnlineService;
import pitslify.api.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:5173",
        "https://food-facil-painel-admin-8mcqkbvbs-eliezerbrasilians-projects.vercel.app",
        "https://food-facil-painel-admin.vercel.app", "https://foodfacil-website.vercel.app"})
@RestController
@RequestMapping(AppUtils.baseUrl + "/online")

public class OnlineController {
    @Autowired
    OnlineService onlineService;

    @GetMapping("{id}")
    ResponseEntity<Object> consultaOnline(@PathVariable String id){
        return onlineService.consultaOnline(id);
    }

    @PostMapping("cria")
    ResponseEntity<Object> cria(){
        return onlineService.criaOnline();
    }
    @PutMapping("ativa/{id}")
    ResponseEntity<Object> ativaOnline(@PathVariable String id){
        return onlineService.ativaOnline(id);
    }
    @PutMapping("desativa/{id}")
    ResponseEntity<Object> desativaOnline(@PathVariable String id){
        return onlineService.desativaOnline(id);
    }
}
