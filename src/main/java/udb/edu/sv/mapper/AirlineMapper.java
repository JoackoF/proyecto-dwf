package udb.edu.sv.mapper;

import org.mapstruct.Mapper;
import udb.edu.sv.dto.AirlineDTO;
import udb.edu.sv.entity.Airline;

@Mapper(componentModel = "spring")
public interface AirlineMapper {

    AirlineDTO toDTO(Airline airline);

    Airline toEntity(AirlineDTO dto);

}