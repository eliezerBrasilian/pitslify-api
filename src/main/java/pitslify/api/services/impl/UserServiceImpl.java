package pitslify.api.services.impl;

import pitslify.api.records.Address;
import pitslify.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pitslify.api.dtos.ProfilePhotoDto;
import pitslify.api.repositories.UserRepository;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    PixPaymentGatewayImpl pixPaymentGateway;

    @Autowired
    UserRepository userRepository;

    @Override
    public ResponseEntity<Object> updatePhoto(ProfilePhotoDto profilePhotoDto) {
        var optionalUser = userRepository.findById(profilePhotoDto.userUid());

        try {
            if (optionalUser.isPresent()) {
                System.out.println("usuario existe");
                var user = optionalUser.get();
                user.setProfilePicture(profilePhotoDto.newProfilePhoto());

                userRepository.save(user);
                var data = Map.of("message", "foto de perfil atualizada");
                return ResponseEntity.ok().body(data);
            } else {
                var data = Map.of("message", "usuario não existe");
                return ResponseEntity.ok().body(data);
            }
        } catch (Exception e) {
            var data = Map.of("message", e.getMessage(), "causa", e.getCause());
            return ResponseEntity.badRequest().body(data);
        }
    }

    @Override
    public ResponseEntity<Object> updateAddress(Address address, String userId) {
        var optionalUser = userRepository.findById(userId);

        try {
            if (optionalUser.isEmpty()) {
                var data = Map.of("message", "usuario não existe");
                return ResponseEntity.ok().body(data);
            } else {
                System.out.println("usuario existe");
                var user = optionalUser.get();
                user.setAddress(address);

                userRepository.save(user);
                var data = Map.of("message", "endereço atualizado");
                return ResponseEntity.ok().body(data);
            }
        } catch (Exception e) {
            var data = Map.of("message", e.getMessage(), "causa", e.getCause());
            System.out.println(data);
            return ResponseEntity.badRequest().body(data);
        }
    }

//    @Override
//    public ResponseEntity<Object> getPedidos(String userId) {
//        var optionalUser = userRepository.findById(userId);
//
//        if (optionalUser.isEmpty()) {
//            return ResponseEntity.badRequest().body(Map.of("message", "usuário não existe"));
//        }
//
//        try {
//            var pedidos = paymentRequestRepository.findByUserIdOrderByCreatedAtDesc(userId);
//
//            return ResponseEntity.ok().body(pedidos);
//        } catch (RuntimeException e) {
//            throw new RuntimeException("excessao ocorreu ao tentar buscar os pedidos do usuario: " + e.getMessage());
//        }
//
//    }

}