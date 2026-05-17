package udb.edu.sv.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import udb.edu.sv.entity.enums.PaymentType;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequestDTO {

    @NotNull(message = "El ID de la reserva es obligatorio")
    @Min(value = 1, message = "El ID de la reserva debe ser positivo")
    private Long reservationId;

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor que cero")
    @DecimalMax(value = "99999.99", message = "El monto no puede superar 99,999.99")
    @Digits(integer = 5, fraction = 2, message = "El monto admite máximo 5 enteros y 2 decimales")
    private BigDecimal amount;

    @NotNull(message = "El tipo de pago es obligatorio (CARD, TRANSFER, CASH)")
    private PaymentType paymentType;
}
