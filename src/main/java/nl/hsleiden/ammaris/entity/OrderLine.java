package nl.hsleiden.ammaris.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_line")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Parent order
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id",
            foreignKey = @ForeignKey(name = "fk_line_order"))
    private Order orderRef;

    // Snapshot of product at purchase time
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id",
            foreignKey = @ForeignKey(name = "fk_line_product"))
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    // price * quantity at time of purchase (stored for history)
    @Column(name = "line_total", nullable = false, precision = 12, scale = 2)
    private BigDecimal lineTotal;
}
