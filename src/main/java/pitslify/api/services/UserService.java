package pitslify.api.services;


import pitslify.api.records.Address;
import org.springframework.http.ResponseEntity;
import pitslify.api.dtos.ProfilePhotoDto;

public interface UserService {
    ResponseEntity<Object> updatePhoto(ProfilePhotoDto profilePhotoDto);

    ResponseEntity<Object> updateAddress(Address address, String userId);

}

