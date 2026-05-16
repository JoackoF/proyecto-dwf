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
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String name;

    @NotBlank(message = "El código IATA es obligatorio")
    @Size(max = 10, message = "El código no puede exceder 10 caracteres")
    @Pattern(regexp = "^[A-Z0-9-]+$", message = "El código debe contener solo letras mayúsculas, números o guiones")
    private String code;
}
