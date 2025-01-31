package net.david.todo.service;

import net.david.todo.dto.JwtAuthResponse;
import net.david.todo.dto.LoginDto;
import net.david.todo.dto.RegisterDto;

public interface AuthService {
    public String register(RegisterDto registerDto);
    public JwtAuthResponse login(LoginDto loginDto);
}
