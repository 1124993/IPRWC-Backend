package nl.hsleiden.ammaris.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class UpdateCartItemRequest {

    @NotNull
    @Min(1)
    private Integer quantity;
}
