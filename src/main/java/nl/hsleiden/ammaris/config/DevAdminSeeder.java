package nl.hsleiden.ammaris.config;

import lombok.RequiredArgsConstructor;
import nl.hsleiden.ammaris.entity.AppUser;
import nl.hsleiden.ammaris.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

/**
 * Creates a default ADMIN account when app.admin.seed=true.
 * Safe for dev; disable for production.
 */
@Configuration
@RequiredArgsConstructor
public class DevAdminSeeder {

    private final AppUserRepository users;
    private final PasswordEncoder encoder;

    @Value("${app.admin.seed:false}")
    private boolean seed;

    @Value("${app.admin.email:}")
    private String email;

    @Value("${app.admin.password:}")
    private String password;

    @Bean
    public ApplicationRunner seedAdminRunner() {
        return args -> {
            if (!seed) return;
            if (!StringUtils.hasText(email) || !StringUtils.hasText(password)) return;

            if (users.existsByEmail(email)) {
                return; // already present
            }

            AppUser admin = AppUser.builder()
                    .email(email)
                    .passwordHash(encoder.encode(password))
                    .role(AppUser.Role.ADMIN)
                    .build();
            users.save(admin);
            System.out.println("Dev admin created: " + email);
        };
    }
}
