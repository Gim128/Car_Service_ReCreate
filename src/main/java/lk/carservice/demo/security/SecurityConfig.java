package lk.carservice.demo.security;

import lk.carservice.demo.entity.User;
import lk.carservice.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/users/signup").permitAll() // Allow signup for everyone
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Admin-only endpoints
                        .requestMatchers("/user/**").hasRole("USER") // User-only endpoints

                        .requestMatchers("/api/admins").hasRole("SUPER_ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/admins").hasRole("SUPER_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/admins/**").hasAnyRole("SUPER_ADMIN", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/admins/**").hasRole("SUPER_ADMIN")
                        .anyRequest().authenticated() // All other requests require authentication
                )
                .httpBasic(withDefaults())


                .formLogin(form -> form
                        .loginPage("/login") // Custom login page
                        .defaultSuccessUrl("/redirect", true) // Redirect based on role
                        .permitAll() // Allow everyone to access the login page
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login") // Redirect to login page after logout
                        .permitAll() // Allow everyone to access the logout endpoint
                );



        return http.build();
    }
    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            User user = userRepository.findByUsername(userRepository.toString())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getEmail())
                    .password(user.getPassword())
                    .roles(user.getRole().name())
                    .build();
        };
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
