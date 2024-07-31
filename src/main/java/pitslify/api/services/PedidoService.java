package pitslify.api.services;

import org.springframework.http.ResponseEntity;


public interface PedidoService {

    ResponseEntity<Object> getAll();

    ResponseEntity<Object> buscaPedido(String id);

    ResponseEntity<Object> confirmaPagamento(String id);

//    ResponseEntity<Object> editaStatus(PedidoRequestEditDto pedidoRequestEditDto, String id);

    ResponseEntity<Object> deleteAll();

    ResponseEntity<Object> exclui(String id);
}
