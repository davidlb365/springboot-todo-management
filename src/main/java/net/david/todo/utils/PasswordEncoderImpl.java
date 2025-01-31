package net.david.todo.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderImpl {
    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean equalPassword = passwordEncoder.matches("1234", passwordEncoder.encode("1234"));
        System.out.println(equalPassword);
    }
}
