package nl.hsleiden.ammaris.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class AddCartItemRequest {

    @NotNull
    private Long productId;

    @NotNull
    @Min(1)
    private Integer quantity;
}
