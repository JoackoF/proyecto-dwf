package udb.edu.sv.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import udb.edu.sv.entity.enums.PaymentType;

@Data
public class BookingRequestDTO {

    @NotNull(message = "El ID del vuelo es obligatorio")
    @Min(value = 1, message = "El ID del vuelo debe ser positivo")
    private Long flightId;

    @NotBlank(message = "El número de asiento es obligatorio")
    @Size(min = 2, max = 10, message = "El asiento debe tener entre 2 y 10 caracteres")
    @Pattern(regexp = "^[0-9]{1,3}[A-Z]$", message = "Formato de asiento inválido (ej: 1A, 12B, 100C)")
    private String seatNumber;

    @NotNull(message = "El tipo de pago es obligatorio (CARD, TRANSFER, CASH)")
    private PaymentType paymentType;

    @NotNull(message = "Los datos del pasajero son obligatorios")
    @Valid
    private PassengerRequestDTO passenger;
}
