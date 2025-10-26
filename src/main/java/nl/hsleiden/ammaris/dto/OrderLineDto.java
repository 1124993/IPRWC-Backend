package nl.hsleiden.ammaris.dto;

import lombok.*;
import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class OrderLineDto {
    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal lineTotal;
}
