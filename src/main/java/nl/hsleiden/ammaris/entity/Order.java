package nl.hsleiden.ammaris.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Who placed this order
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",
            foreignKey = @ForeignKey(name = "fk_order_user"))
    private AppUser user;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;
}
