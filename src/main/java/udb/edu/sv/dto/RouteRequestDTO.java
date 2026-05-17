package udb.edu.sv.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteRequestDTO {

    @NotBlank(message = "El origen es obligatorio")
    @Size(min = 2, max = 100, message = "El origen debe tener entre 2 y 100 caracteres")
    @Pattern(regexp = "^[\\p{L} .'-]+$", message = "El origen solo admite letras, espacios y . ' -")
    private String origin;

    @NotBlank(message = "El destino es obligatorio")
    @Size(min = 2, max = 100, message = "El destino debe tener entre 2 y 100 caracteres")
    @Pattern(regexp = "^[\\p{L} .'-]+$", message = "El destino solo admite letras, espacios y . ' -")
    private String destination;

    @NotNull(message = "La duración en minutos es obligatoria")
    @Min(value = 1, message = "La duración debe ser al menos 1 minuto")
    @Max(value = 1440, message = "La duración no puede superar 1440 minutos (24h)")
    private Integer durationMinutes;
}
