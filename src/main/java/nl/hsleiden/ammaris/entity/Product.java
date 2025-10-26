package nl.hsleiden.ammaris.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class Product {

    public enum Category {
        MEN, WOMEN, UNISEX
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 120)
    private String name;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "archived", nullable = false)
    private boolean archived = false;

    @Column(name = "image_url", length = 512)
    private String imageUrl;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    // 🔒 NOW REQUIRED IN DB
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false, length = 10)
    private Category category = Category.UNISEX; // default in code so inserts never fail

    // Optional convenience constructor
    public Product(String name, BigDecimal price, Integer stock, boolean archived,
                   String imageUrl, String description, Category category) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.archived = archived;
        this.imageUrl = imageUrl;
        this.description = description;
        this.category = (category != null ? category : Category.UNISEX);
    }
}
