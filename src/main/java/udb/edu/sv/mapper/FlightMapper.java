package udb.edu.sv.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import udb.edu.sv.dto.FlightDTO;
import udb.edu.sv.entity.Flight;

@Mapper(componentModel = "spring")
public interface FlightMapper {

    @Mapping(source = "airline.id", target = "airlineId")
    @Mapping(source = "aircraft.id", target = "aircraftId")
    @Mapping(source = "route.id", target = "routeId")
    FlightDTO toDTO(Flight flight);

    @Mapping(source = "airlineId", target = "airline.id")
    @Mapping(source = "aircraftId", target = "aircraft.id")
    @Mapping(source = "routeId", target = "route.id")
    Flight toEntity(FlightDTO dto);
}