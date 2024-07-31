package pitslify.api.services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;


public interface OnlineService {
    ResponseEntity<Object> consultaOnline(@PathVariable String id);

    ResponseEntity<Object> desativaOnline(@PathVariable String id);

    ResponseEntity<Object> ativaOnline(@PathVariable String id);

    ResponseEntity<Object> criaOnline();
}
