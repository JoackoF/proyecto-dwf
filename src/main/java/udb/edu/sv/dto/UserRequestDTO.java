package udb.edu.sv.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import udb.edu.sv.entity.enums.UserRole;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDTO {

    @NotBlank(message = "El nombre completo es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String fullName;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe tener un formato válido")
    @Size(max = 100, message = "El email no puede exceder 100 caracteres")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, max = 100, message = "La contraseña debe tener entre 6 y 100 caracteres")
    private String password;

    @NotNull(message = "El rol es obligatorio (ADMIN, EMPLOYEE, CUSTOMER)")
    private UserRole role;
}
