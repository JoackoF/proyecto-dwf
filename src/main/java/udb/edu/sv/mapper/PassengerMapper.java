package udb.edu.sv.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import udb.edu.sv.dto.PassengerRequestDTO;
import udb.edu.sv.dto.PassengerResponseDTO;
import udb.edu.sv.entity.Passenger;

@Mapper(componentModel = "spring")
public interface PassengerMapper {

    PassengerResponseDTO toResponseDTO(Passenger passenger);

    @Mapping(target = "id", ignore = true)
    Passenger toEntity(PassengerRequestDTO dto);
}
