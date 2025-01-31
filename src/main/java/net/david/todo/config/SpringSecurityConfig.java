package net.david.todo.config;

import lombok.AllArgsConstructor;
import net.david.todo.security.CustomUserDetailsService;
import net.david.todo.security.JwtAuthenticationEntryPoint;
import net.david.todo.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity   // enable method level security in controller with @PreAuthorize
@AllArgsConstructor
public class SpringSecurityConfig {

    private UserDetailsService userDetailsService;   // From Spring Security 6, it will be used automatically by AuthenticationManager
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private JwtAuthenticationFilter authenticationFilter;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> {
//                    authorize.requestMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN");
//                    authorize.requestMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN");
//                    authorize.requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN");
//                    authorize.requestMatchers(HttpMethod.GET, "/api/**").hasAnyRole("USER", "ADMIN");
//                    authorize.requestMatchers(HttpMethod.PATCH, "/api/**").hasAnyRole("USER", "ADMIN");
//                    authorize.requestMatchers(HttpMethod.GET, "/api/**").permitAll(); // can access without user authentication
                    authorize.requestMatchers( "/api/auth/**").permitAll();
                    authorize.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();   // for preflight request
                    authorize.anyRequest().authenticated();
                })
//                .cors().and()
                .httpBasic(Customizer.withDefaults());

        http.exceptionHandling(exception -> exception
                .authenticationEntryPoint(jwtAuthenticationEntryPoint));
        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails david = User.builder()
//                .username("david")
//                .password(passwordEncoder().encode("1234"))
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("1234"))
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(david, admin);
//    }
}
