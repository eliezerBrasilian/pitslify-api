package pitslify.api.services;

import org.springframework.http.ResponseEntity;

public interface SalgadoService {

    ResponseEntity<Object> deleteAll();

    ResponseEntity<Object> excluiSalgado(String id);

    ResponseEntity<Object> salgadosList();
}
