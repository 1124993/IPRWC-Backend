package nl.hsleiden.ammaris.security;

import nl.hsleiden.ammaris.entity.AppUser;
import nl.hsleiden.ammaris.repository.AppUserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserDetailsService implements UserDetailsService {

    private final AppUserRepository users;

    public AppUserDetailsService(AppUserRepository users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser u = users.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("No user with email " + email));

        // Build a Spring Security user with authorities based on role.
        return User.builder()
                .username(u.getEmail())
                .password(u.getPasswordHash())
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_" + u.getRole().name())))
                .build();
    }
}

