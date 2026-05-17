package udb.edu.sv.dto;

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
public class ReservationRequestDTO {

    @NotNull(message = "El ID del vuelo es obligatorio")
    @Min(value = 1, message = "El ID del vuelo debe ser positivo")
    private Long flightId;

    @NotNull(message = "El ID del pasajero es obligatorio")
    @Min(value = 1, message = "El ID del pasajero debe ser positivo")
    private Long passengerId;

    @NotBlank(message = "El número de asiento es obligatorio")
    @Size(min = 2, max = 10, message = "El asiento debe tener entre 2 y 10 caracteres")
    @Pattern(regexp = "^[0-9]{1,3}[A-Z]$", message = "Formato de asiento inválido (ej: 1A, 12B, 100C)")
    private String seatNumber;
}
