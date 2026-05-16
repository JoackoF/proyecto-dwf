package udb.edu.sv.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AircraftRequestDTO {

    private String model;
    private Integer capacity;
    private Long airlineId;
}
