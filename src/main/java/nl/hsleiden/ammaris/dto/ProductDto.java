package nl.hsleiden.ammaris.dto;

import lombok.*;
import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private Integer stock;

    // NEW
    private String imageUrl;
    private String description;
    private String category; // "MEN" | "WOMEN" | "UNISEX"
    private boolean archived;


}
