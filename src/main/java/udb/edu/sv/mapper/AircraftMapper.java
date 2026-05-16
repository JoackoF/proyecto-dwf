package udb.edu.sv.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import udb.edu.sv.dto.AircraftRequestDTO;
import udb.edu.sv.dto.AircraftResponseDTO;
import udb.edu.sv.entity.Aircraft;

@Mapper(componentModel = "spring")
public interface AircraftMapper {

    @Mapping(source = "airline.id", target = "airlineId")
    AircraftResponseDTO toResponseDTO(Aircraft aircraft);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "airline", ignore = true)
    Aircraft toEntity(AircraftRequestDTO dto);
}
