package udb.edu.sv.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirlineResponseDTO {

    private Long id;
    private String name;
    private String code;
}
