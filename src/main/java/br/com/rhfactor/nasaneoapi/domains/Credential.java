package br.com.rhfactor.nasaneoapi.domains;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "credential", uniqueConstraints = {
        @UniqueConstraint(name = "UN_credential_username", columnNames = {"username"}),
})

@Data
@Builder(toBuilder = true)
@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Credential implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    @Length(max = 100)
    @Column(length = 100, unique = true, nullable = false)
    String username;

    @NotNull
    @Length(max = 80)
    @Column(length = 80, nullable = false)
    String password;

    @Builder.Default
    Boolean active = true;

    @Override
    public List<GrantedAuthority> getAuthorities() {
        return Arrays.asList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
