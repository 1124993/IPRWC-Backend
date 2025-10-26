package nl.hsleiden.ammaris.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "app_user", uniqueConstraints = {
        @UniqueConstraint(name = "uk_app_user_email", columnNames = "email")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String email;

    @Column(nullable = false, length = 100)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;

    public enum Role {
        USER, ADMIN
    }
}
