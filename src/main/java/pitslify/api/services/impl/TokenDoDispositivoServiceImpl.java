package pitslify.api.services.impl;

import pitslify.api.dtos.TokenDoDispositivoRequestDto;
import pitslify.api.models.TokenDoDIspositivo;
import pitslify.api.repositories.TokenDoDispositivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TokenDoDispositivoServiceImpl {
    @Autowired
    TokenDoDispositivoRepository tokenDoDispositivoRepository;

    public ResponseEntity<Object> salvaOuAtualiza(TokenDoDispositivoRequestDto tokenDoDispositivoRequestDto){
        try{
           var optional = tokenDoDispositivoRepository.findByUserId(tokenDoDispositivoRequestDto.userId());
           if(optional.isEmpty()){
               //salva
               var tokenDoDIspositivo = tokenDoDispositivoRepository.save(new TokenDoDIspositivo(tokenDoDispositivoRequestDto));
               var data = Map.of("message", "salgado salvo com sucesso",
                       "id", tokenDoDIspositivo.getId());

               return ResponseEntity.ok().body(data);
           }else{
               //atualiza

              var tokenDoDispositivoEncontrado = optional.get();
              tokenDoDispositivoEncontrado.setToken(tokenDoDispositivoEncontrado.getToken());

               var tokenDoDIspositivo = tokenDoDispositivoRepository.save(tokenDoDispositivoEncontrado);

               var data = Map.of("message", "salgado atualizado com sucesso",
                       "id", tokenDoDIspositivo.getId());
               return ResponseEntity.ok().body(data);
           }

        }catch (RuntimeException e){
            throw new RuntimeException("falha ao salvar devido a uma excessao: "+e.getMessage());
        }

    }

    public ResponseEntity<Object> getAll(){
        var list = tokenDoDispositivoRepository.findAll();


        return ResponseEntity.ok().body(list);
    }
}
