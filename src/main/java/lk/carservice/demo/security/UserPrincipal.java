package lk.carservice.demo.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lk.carservice.demo.entity.User;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class UserPrincipal implements UserDetails {


    private final Long id;
    @Getter
    private final String email;

    private String username;

    @JsonIgnore
    private final String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(Integer id, String username, String email, String password,
                         Collection<? extends GrantedAuthority> authorities) {
        this.id = Long.valueOf(id);
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    private UserPrincipal userPrincipal;
//    Authentication auth = new UsernamePasswordAuthenticationToken(
//            userPrincipal,
//            userPrincipal.getPassword(),
//            userPrincipal.getAuthorities()
//    );

    public static UserPrincipal create(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().toString()))
                .collect(Collectors.toList());

        return new UserPrincipal(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username; // Or return username if you prefer
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

    public Long getUserId() { return id; }

}
