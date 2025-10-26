package nl.hsleiden.ammaris.controller;

import nl.hsleiden.ammaris.dto.AuthResponse;
import nl.hsleiden.ammaris.dto.LoginRequest;
import nl.hsleiden.ammaris.dto.SignupRequest;
import nl.hsleiden.ammaris.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import nl.hsleiden.ammaris.dto.MeResponse;
import org.springframework.security.core.GrantedAuthority;
import java.util.Optional;


import jakarta.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService auth;

    public AuthController(AuthService auth) {
        this.auth = auth;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest req) {
        auth.signup(req);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest req) {
        return ResponseEntity.ok(auth.login(req));
    }

    /**
     * Protected endpoint: requires a valid Bearer token.
     * We DID NOT permit this path in SecurityConfig.
     */
    @GetMapping("/me")
    public ResponseEntity<?> me() {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();

        // principal is the username, which you set to email
        String email = (a != null) ? a.getName() : null;

        // Authorities were set by AppUserDetailsService as ROLE_<ROLE_NAME>
        String role = null;
        if (a != null && a.getAuthorities() != null) {
            role = a.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)     // e.g. "ROLE_ADMIN"
                    .filter(auth -> auth != null && !auth.isBlank())
                    .map(auth -> auth.startsWith("ROLE_") ? auth.substring(5) : auth) // -> "ADMIN"
                    .findFirst()
                    .orElse(null);
        }

        return ResponseEntity.ok(new MeResponse(email, role));
    }

}
