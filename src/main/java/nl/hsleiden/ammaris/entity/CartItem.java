package nl.hsleiden.ammaris.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cart_item",
        uniqueConstraints = @UniqueConstraint(name = "uk_cart_user_product",
                columnNames = {"user_id","product_id"}))
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Owner of this cart row
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_cart_user"))
    private AppUser user;

    // Product referenced
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_cart_product"))
    private Product product;

    @Column(nullable = false)
    private Integer quantity;
}
