package nl.hsleiden.ammaris.dto;

import lombok.*;

import java.time.OffsetDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AdminOrderSummaryDto {
    private Long id;
    private String userEmail;
    private OffsetDateTime createdAt;
}
