package pitslify.api.services;


import pitslify.api.records.Address;
import org.springframework.http.ResponseEntity;
import pitslify.api.dtos.ProfilePhotoDto;
import pitslify.api.dtos.TokenDoDispositivoRequestDto;

public interface UserService {
    ResponseEntity<Object> updatePhoto(ProfilePhotoDto profilePhotoDto);

    ResponseEntity<Object> updateAddress(Address address, String userId);

//    ResponseEntity<Object> addCupom(UserCupomDto userCupom);
//
//    ResponseEntity<Object> usarCupom(CupomToUpdateDto cupomToUpdateDto);

//    ResponseEntity<Object> getPedidos(String userId);

    ResponseEntity<Object> salvaOuAtualizaToken(TokenDoDispositivoRequestDto tokenDoDispositivoRequestDto);
}

