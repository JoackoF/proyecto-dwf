package udb.edu.sv.mapper;

import org.mapstruct.Mapper;
import udb.edu.sv.dto.RouteDTO;
import udb.edu.sv.entity.Route;

@Mapper(componentModel = "spring")
public interface RouteMapper {

    RouteDTO toDTO(Route route);

    Route toEntity(RouteDTO dto);

}