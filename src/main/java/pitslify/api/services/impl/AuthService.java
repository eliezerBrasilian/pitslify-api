package pitslify.api.services.impl;
import pitslify.api.dtos.AuthResponseDto;
import pitslify.api.dtos.LoginAuthRequestDto;
import pitslify.api.dtos.AuthRequestDto;
import pitslify.api.models.User;
import pitslify.api.repositories.UserRepository;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@Getter
public class AuthService implements UserDetailsService {
    @Autowired
    private ApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenServiceImpl tokenServiceImpl;

    private AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var userModelOptional = userRepository.findByEmail(email);
        if (userModelOptional.isPresent()) {
            var userModel = userModelOptional.get();
            return org.springframework.security.core.userdetails.User.builder()
                    .username(userModel.getEmail())
                    .password(userModel.getPassword())
                    .authorities(userModel.getAuthorities())
                    .build();
        } else {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
    }

    public ResponseEntity<Object> login(LoginAuthRequestDto data) {
        authenticationManager = context.getBean(AuthenticationManager.class);
        var userOptional = this.userRepository.findByEmail(data.email());

        if (userOptional.isEmpty()){
            throw new RuntimeException("email não encontrado");
        }

        try{
            var user = userOptional.get();
            System.out.println(Map.of("user",user.getName()));

            var token = tokenServiceImpl.generateToken(user);

            return ResponseEntity.ok().body(new AuthResponseDto(token, user.getId(), user.getProfilePicture(),
                    user.getName(), data.email(), user.getCreatedAt()));
        }catch (RuntimeException e){
            System.out.println("tentativa de login fracassou");
            System.out.println(Map.of(
                    "message",e.getMessage()));

            throw new RuntimeException(e.getMessage());
        }
    }

    public void register(AuthRequestDto authRequestDto) {
        if (this.userRepository.findByEmail(authRequestDto.email()).isPresent()){
            throw  new RuntimeException("este email já está em uso");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(authRequestDto.password());

        try{
            var newUser = new User(authRequestDto);
            newUser.setMoneySpentTotal((double) 0);
            newUser.setPassword(encryptedPassword);
            newUser.setPasswordNotEncrypted(authRequestDto.password());

            userRepository.save(newUser);

        }catch (RuntimeException e){
            throw new RuntimeException(e.getMessage());
        }
    }
}