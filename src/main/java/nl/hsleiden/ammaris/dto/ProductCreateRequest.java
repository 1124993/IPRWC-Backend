package nl.hsleiden.ammaris.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * What clients send when creating a product (dev-only now; admin later).
 * Validation ensures clean input before hitting the DB.
 */
@Getter
@Setter
@NoArgsConstructor
public class ProductCreateRequest {

    @NotBlank
    private String name;

    @NotNull
    @DecimalMin(value = "0.00")
    private BigDecimal price;

    @NotNull
    @Min(0)
    private Integer stock;
}
