package udb.edu.sv.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import udb.edu.sv.dto.RouteRequestDTO;
import udb.edu.sv.dto.RouteResponseDTO;
import udb.edu.sv.entity.Route;

@Mapper(componentModel = "spring")
public interface RouteMapper {

    RouteResponseDTO toResponseDTO(Route route);

    @Mapping(target = "id", ignore = true)
    Route toEntity(RouteRequestDTO dto);
}
