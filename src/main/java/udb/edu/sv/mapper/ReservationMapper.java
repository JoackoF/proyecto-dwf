package udb.edu.sv.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import udb.edu.sv.dto.ReservationDTO;
import udb.edu.sv.entity.Reservation;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    @Mapping(source = "flight.id", target = "flightId")
    ReservationDTO toDTO(Reservation reservation);

    @Mapping(source = "flightId", target = "flight.id")
    Reservation toEntity(ReservationDTO dto);
}