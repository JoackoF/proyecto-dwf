package udb.edu.sv.mapper;

import org.mapstruct.Mapper;
import udb.edu.sv.dto.PassengerDTO;
import udb.edu.sv.entity.Passenger;

@Mapper(componentModel = "spring")
public interface PassengerMapper {

    PassengerDTO toDTO(Passenger passenger);

    Passenger toEntity(PassengerDTO dto);
}