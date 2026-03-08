package udb.edu.sv.mapper;

import org.mapstruct.Mapper;
import udb.edu.sv.dto.AircraftDTO;
import udb.edu.sv.entity.Aircraft;

@Mapper(componentModel = "spring")
public interface AircraftMapper {

    AircraftDTO toDTO(Aircraft aircraft);

    Aircraft toEntity(AircraftDTO dto);
}