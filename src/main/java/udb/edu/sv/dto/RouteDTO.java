package udb.edu.sv.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteDTO {

    private Long id;
    private String origin;
    private String destination;
    private Integer durationMinutes;
}