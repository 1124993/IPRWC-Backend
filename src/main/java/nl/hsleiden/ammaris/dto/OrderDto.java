package nl.hsleiden.ammaris.dto;

import lombok.*;
import java.time.OffsetDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class OrderDto {
    private Long id;
    private OffsetDateTime createdAt;
}
