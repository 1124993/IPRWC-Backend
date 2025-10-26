package nl.hsleiden.ammaris.service;

import nl.hsleiden.ammaris.dto.AuthResponse;
import nl.hsleiden.ammaris.dto.LoginRequest;
import nl.hsleiden.ammaris.dto.SignupRequest;
import nl.hsleiden.ammaris.entity.AppUser;
import nl.hsleiden.ammaris.repository.AppUserRepository;
import nl.hsleiden.ammaris.security.JwtService;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AppUserRepository users;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final JwtService jwt;

    public AuthService(AppUserRepository users,
                       PasswordEncoder encoder,
                       AuthenticationManager authManager,
                       JwtService jwt) {
        this.users = users;
        this.encoder = encoder;
        this.authManager = authManager;
        this.jwt = jwt;
    }

    public void signup(SignupRequest req) {
        if (users.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }
        AppUser u = AppUser.builder()
                .email(req.getEmail())
                .passwordHash(encoder.encode(req.getPassword()))
                .role(AppUser.Role.USER)
                .build();
        users.save(u);
    }

    public AuthResponse login(LoginRequest req) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
        );
        // If we reach here, credentials are valid.
        String token = jwt.generateToken(req.getEmail());
        return new AuthResponse(token);
    }
}
