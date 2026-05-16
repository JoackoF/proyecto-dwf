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
public class AircraftRequestDTO {

    @NotBlank(message = "El modelo de la aeronave es obligatorio")
    @Size(max = 100, message = "El modelo no puede exceder 100 caracteres")
    private String model;

    @NotNull(message = "La capacidad es obligatoria")
    @Min(value = 1, message = "La capacidad debe ser un entero positivo")
    private Integer capacity;

    @NotNull(message = "El ID de la aerolínea es obligatorio")
    @Min(value = 1, message = "El ID de la aerolínea debe ser positivo")
    private Long airlineId;
}
