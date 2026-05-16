package udb.edu.sv.dto;

import lombok.*;
import udb.edu.sv.entity.enums.ClaimStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClaimResponseDTO {

    private Long id;
    private Long reservationId;
    private String description;
    private ClaimStatus status;
    private LocalDateTime createdAt;
}
