package udb.edu.sv.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PassengerRequestDTO {

    @NotBlank(message = "El nombre completo es obligatorio")
    @Size(min = 2, max = 150, message = "El nombre debe tener entre 2 y 150 caracteres")
    @Pattern(regexp = "^[\\p{L} .'-]+$", message = "El nombre solo admite letras, espacios y . ' -")
    private String fullName;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe estar en el pasado")
    private LocalDate birthDate;

    @NotBlank(message = "El número de pasaporte es obligatorio")
    @Size(min = 5, max = 50, message = "El pasaporte debe tener entre 5 y 50 caracteres")
    @Pattern(regexp = "^[A-Z0-9-]+$", message = "El pasaporte solo admite letras mayúsculas, números y guiones")
    private String passportNumber;
}
