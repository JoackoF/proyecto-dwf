package udb.edu.sv.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AircraftResponseDTO {

    private Long id;
    private String model;
    private Integer capacity;
    private Long airlineId;
}
