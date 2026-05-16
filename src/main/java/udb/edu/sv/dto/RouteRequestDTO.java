package udb.edu.sv.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteRequestDTO {

    private String origin;
    private String destination;
    private Integer durationMinutes;
}
