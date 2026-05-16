package udb.edu.sv.dto;

import lombok.*;
import udb.edu.sv.entity.enums.PaymentType;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequestDTO {

    private Long reservationId;
    private BigDecimal amount;
    private PaymentType paymentType;
}
