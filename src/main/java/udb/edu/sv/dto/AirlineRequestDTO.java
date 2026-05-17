package udb.edu.sv.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirlineRequestDTO {

    @NotBlank(message = "El nombre de la aerolínea es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String name;

    @NotBlank(message = "El código IATA es obligatorio")
    @Size(min = 2, max = 3, message = "El código IATA debe tener 2 o 3 caracteres")
    @Pattern(regexp = "^[A-Z0-9]{2,3}$", message = "El código IATA debe ser 2 o 3 caracteres mayúsculos/números (ej: AV, CM, IB)")
    private String code;
}
