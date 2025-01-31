package net.david.todo.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.david.todo.dto.JwtAuthResponse;
import net.david.todo.dto.LoginDto;
import net.david.todo.dto.RegisterDto;
import net.david.todo.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
public class AuthController {
    private AuthService authService;

    // Build Register REST API
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDto registerDto) {
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Build Login REST API
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@Valid @RequestBody LoginDto loginDto) {
        JwtAuthResponse jwtAuthResponse = authService.login(loginDto);
        return ResponseEntity.ok(jwtAuthResponse);
    }
}
