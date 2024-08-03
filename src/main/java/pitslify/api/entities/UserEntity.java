package pitslify.api.entities;

import pitslify.api.records.Address;
import pitslify.api.dtos.AuthRequestDto;
import pitslify.api.enums.UserRole;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Document(collection = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class UserEntity implements UserDetails {
    @Id
    private String id;
    private String email;
    private String name;
    private String password;
    private String userRole;
    private String profilePicture;
    private long createdAt;
    private Date updatedAt;
    @Field("address")
    private Address address;
    private Double moneySpentTotal;
    private String passwordNotEncrypted;
    private Boolean isNewClient;

    public UserEntity(AuthRequestDto authRequestDto) {
        this.email = authRequestDto.email();
        this.password = authRequestDto.password();
        this.userRole = authRequestDto.role().getRole();
        this.name = authRequestDto.name();
        this.profilePicture = authRequestDto.profilePicture();
        this.createdAt = System.currentTimeMillis();
        this.isNewClient = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (Objects.equals(this.userRole, UserRole.ADMIN.getRole()))
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;}

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}