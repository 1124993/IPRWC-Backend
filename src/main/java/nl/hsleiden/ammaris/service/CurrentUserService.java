package nl.hsleiden.ammaris.service;

import lombok.RequiredArgsConstructor;
import nl.hsleiden.ammaris.entity.AppUser;
import nl.hsleiden.ammaris.repository.AppUserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserService {

    private final AppUserRepository users;

    public AppUser requireCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null) {
            throw new IllegalStateException("No authenticated user in context");
        }
        String email = auth.getName();
        return users.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Authenticated user not found: " + email));
    }
}
