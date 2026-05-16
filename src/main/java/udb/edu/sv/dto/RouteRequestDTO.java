package udb.edu.sv.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteRequestDTO {

    @NotBlank(message = "El origen es obligatorio")
    @Size(max = 100, message = "El origen no puede exceder 100 caracteres")
    private String origin;

    @NotBlank(message = "El destino es obligatorio")
    @Size(max = 100, message = "El destino no puede exceder 100 caracteres")
    private String destination;

    @NotNull(message = "La duración en minutos es obligatoria")
    @Min(value = 1, message = "La duración debe ser un entero positivo")
    private Integer durationMinutes;
}
