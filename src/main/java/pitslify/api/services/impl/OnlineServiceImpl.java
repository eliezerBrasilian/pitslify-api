package pitslify.api.services.impl;

import pitslify.api.enums.OnlineStatus;
import pitslify.api.models.Online;
import pitslify.api.repositories.OnlineRepository;
import pitslify.api.services.OnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OnlineServiceImpl implements OnlineService {
    @Autowired
    OnlineRepository onlineRepository;

    @Override
    public ResponseEntity<Object> criaOnline(){
        try{
            var onlineModel = new Online();
            onlineModel.setOnlineStatus(OnlineStatus.ONLINE);
            var or = onlineRepository.save(onlineModel);
            return ResponseEntity.ok().body(Map.of("id_online",or.getId()));
        }catch (RuntimeException e){
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public ResponseEntity<Object> ativaOnline(String id){
        try{
            var optionalOnline = onlineRepository.findById(id);
            if(optionalOnline.isEmpty()){
                return ResponseEntity.badRequest().body("id_online nao existe");
            }
            var online = optionalOnline.get();
            online.setOnlineStatus(OnlineStatus.ONLINE);
            onlineRepository.save(online);
            return ResponseEntity.ok().body("sucesso, ativado com sucesso");
        }catch (RuntimeException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> desativaOnline(String id){
        try{
            var optionalOnline = onlineRepository.findById(id);
            if(optionalOnline.isEmpty()){
                return ResponseEntity.badRequest().body("id_online nao existe");
            }
            var online = optionalOnline.get();
            online.setOnlineStatus(OnlineStatus.OFFLINE);
            onlineRepository.save(online);
            return ResponseEntity.ok().body("sucesso, desativado com sucesso");
        }catch (RuntimeException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> consultaOnline(String id){
        try{
            var optionalOnline = onlineRepository.findById(id);
            if(optionalOnline.isEmpty()){
                return ResponseEntity.badRequest().body("id_online nao existe");
            }
            var online = optionalOnline.get();
            return ResponseEntity.ok().body(online.getOnlineStatus());
        }catch (RuntimeException e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
