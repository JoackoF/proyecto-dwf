package udb.edu.sv.dto;

import lombok.Data;
import udb.edu.sv.entity.enums.PaymentType;

import java.math.BigDecimal;

@Data
public class PaymentDTO {

    private Long id;

    private Long reservationId;

    private BigDecimal amount;

    private PaymentType paymentType;

    private String status;
}