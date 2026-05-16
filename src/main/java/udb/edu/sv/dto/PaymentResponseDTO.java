package udb.edu.sv.dto;

import lombok.*;
import udb.edu.sv.entity.enums.PaymentType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponseDTO {

    private Long id;
    private Long reservationId;
    private BigDecimal amount;
    private PaymentType paymentType;
    private LocalDateTime paymentDate;
}
