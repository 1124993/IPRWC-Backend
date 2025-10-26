package nl.hsleiden.ammaris.dto;

import lombok.*;
import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class CartItemDto {
    private Long productId;
    private String productName;
    private BigDecimal price;   // unit price (current)
    private Integer quantity;
    private BigDecimal lineTotal;
}
