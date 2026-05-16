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
public class ReservationRequestDTO {

    @NotNull(message = "El ID del vuelo es obligatorio")
    @Min(value = 1, message = "El ID del vuelo debe ser positivo")
    private Long flightId;

    @NotNull(message = "El ID del pasajero es obligatorio")
    @Min(value = 1, message = "El ID del pasajero debe ser positivo")
    private Long passengerId;

    private Long userId;

    @NotBlank(message = "El número de asiento es obligatorio")
    @Size(max = 10, message = "El número de asiento no puede exceder 10 caracteres")
    private String seatNumber;
}
