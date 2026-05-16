package udb.edu.sv.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import udb.edu.sv.dto.PaymentRequestDTO;
import udb.edu.sv.dto.PaymentResponseDTO;
import udb.edu.sv.entity.Payment;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(source = "reservation.id", target = "reservationId")
    PaymentResponseDTO toResponseDTO(Payment payment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reservation", ignore = true)
    @Mapping(target = "paymentDate", ignore = true)
    Payment toEntity(PaymentRequestDTO dto);
}
