package udb.edu.sv.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import udb.edu.sv.dto.AircraftDTO;
import udb.edu.sv.entity.Aircraft;

@Mapper(componentModel = "spring")
public interface AircraftMapper {

    @Mapping(source = "airline.id", target = "airlineId")
    AircraftDTO toDTO(Aircraft aircraft);

    @Mapping(source = "airlineId", target = "airline.id")
    Aircraft toEntity(AircraftDTO dto);

}