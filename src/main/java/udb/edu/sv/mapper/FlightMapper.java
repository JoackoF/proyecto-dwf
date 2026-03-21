package udb.edu.sv.mapper;

import org.springframework.stereotype.Component;
import udb.edu.sv.dto.FlightDTO;
import udb.edu.sv.entity.Flight;

@Component
public class FlightMapper {

    public FlightDTO toDTO(Flight flight) {
        if (flight == null) return null;

        return FlightDTO.builder()
                .id(flight.getId())
                .airlineId(flight.getAirline() != null ? flight.getAirline().getId() : null)
                .aircraftId(flight.getAircraft() != null ? flight.getAircraft().getId() : null)
                .routeId(flight.getRoute() != null ? flight.getRoute().getId() : null)
                .departureDate(flight.getDepartureDate())
                .departureTime(flight.getDepartureTime())
                .price(flight.getPrice())
                .availableSeats(flight.getAvailableSeats())
                .build();
    }

    public Flight toEntity(FlightDTO dto) {
        if (dto == null) return null;

        return Flight.builder()
                .id(dto.getId())
                .departureDate(dto.getDepartureDate())
                .departureTime(dto.getDepartureTime())
                .price(dto.getPrice())
                .availableSeats(dto.getAvailableSeats())
                .build();
    }
}