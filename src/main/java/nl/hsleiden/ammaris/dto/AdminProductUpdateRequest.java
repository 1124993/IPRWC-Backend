package nl.hsleiden.ammaris.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor
public class AdminProductUpdateRequest {

    @NotBlank(message = "Product name is required")
    private String name;

    @NotNull @DecimalMin(value = "0.00", message = "Price must be >= 0.00")
    private BigDecimal price;

    @NotNull @Min(value = 0, message = "Stock must be >= 0")
    private Integer stock;

    @Size(max = 512, message = "Image URL too long")
    private String imageUrl;

    private String description;

    @Pattern(regexp = "(?i)MEN|WOMEN|UNISEX", message = "Category must be MEN, WOMEN or UNISEX")
    private String category;
}
