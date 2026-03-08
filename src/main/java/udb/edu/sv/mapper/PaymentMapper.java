package udb.edu.sv.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import udb.edu.sv.dto.PaymentDTO;
import udb.edu.sv.entity.Payment;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(source = "reservation.id", target = "reservationId")
    PaymentDTO toDTO(Payment payment);

    @Mapping(source = "reservationId", target = "reservation.id")
    Payment toEntity(PaymentDTO dto);
}