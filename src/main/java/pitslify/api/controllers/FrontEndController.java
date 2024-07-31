package pitslify.api.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrontEndController {

    @RequestMapping(value = "/{path:[^\\.]*}")
    public String redirect() {
        // Redireciona para o index.html para que o front-end lide com as rotas
        return "forward:/index.html";
    }
}
