package udb.edu.sv.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import udb.edu.sv.dto.ReservationRequestDTO;
import udb.edu.sv.dto.ReservationResponseDTO;
import udb.edu.sv.entity.Reservation;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    @Mapping(source = "flight.id", target = "flightId")
    @Mapping(source = "passenger.id", target = "passengerId")
    @Mapping(source = "user.id", target = "userId")
    ReservationResponseDTO toResponseDTO(Reservation reservation);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "flight", ignore = true)
    @Mapping(target = "passenger", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "reservationDate", ignore = true)
    Reservation toEntity(ReservationRequestDTO dto);
}
