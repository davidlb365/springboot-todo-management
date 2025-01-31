package net.david.todo.service.impl;

import lombok.AllArgsConstructor;
import net.david.todo.dto.JwtAuthResponse;
import net.david.todo.dto.LoginDto;
import net.david.todo.dto.RegisterDto;
import net.david.todo.entity.Role;
import net.david.todo.entity.User;
import net.david.todo.exception.TodoAPIException;
import net.david.todo.repository.RoleRepository;
import net.david.todo.repository.UserRepository;
import net.david.todo.security.JwtTokenProvider;
import net.david.todo.service.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private ModelMapper modelMapper;

    @Override
    public String register(RegisterDto registerDto) {
        if(userRepository.existsByUsername(registerDto.getUsername())) throw new TodoAPIException(HttpStatus.BAD_REQUEST, "Username already exists");
        if(userRepository.existsByEmail(registerDto.getEmail())) throw new TodoAPIException(HttpStatus.BAD_REQUEST, "Email already exists");

        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));  // encode password

        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findByName("ROLE_USER");
        roles.add(role);
        user.setRoles(roles);

        userRepository.save(user);

        return "User registered successfully";
    }

    @Override
    public JwtAuthResponse login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(),
                loginDto.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        User user = userRepository.findByUsernameOrEmail(loginDto.getUsernameOrEmail(), loginDto.getUsernameOrEmail()).orElseThrow(() -> new TodoAPIException(HttpStatus.BAD_REQUEST, "User not found"));
        Optional<Role> optionalRole = user.getRoles().stream().findFirst();
        String role = null;
        if(optionalRole.isPresent()) {
            Role userRole = optionalRole.get();
            role = userRole.getName();
        }
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        jwtAuthResponse.setRole(role);
        return jwtAuthResponse;
    }
}
