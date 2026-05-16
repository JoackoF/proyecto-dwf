package udb.edu.sv.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import udb.edu.sv.entity.enums.PaymentType;

@Data
public class BookingRequestDTO {

    @NotNull(message = "El ID del vuelo es obligatorio")
    @Min(value = 1, message = "El ID del vuelo debe ser positivo")
    private Long flightId;

    @NotBlank(message = "El número de asiento es obligatorio")
    @Size(max = 10, message = "El número de asiento no puede exceder 10 caracteres")
    private String seatNumber;

    @NotNull(message = "El tipo de pago es obligatorio (CARD, TRANSFER, CASH)")
    private PaymentType paymentType;

    @NotNull(message = "Los datos del pasajero son obligatorios")
    @Valid
    private PassengerRequestDTO passenger;
}
