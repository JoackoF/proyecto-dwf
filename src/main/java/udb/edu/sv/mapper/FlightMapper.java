package udb.edu.sv.mapper;

import org.springframework.stereotype.Component;
import udb.edu.sv.dto.FlightResponseDTO;
import udb.edu.sv.entity.Flight;

@Component
public class FlightMapper {

    public FlightResponseDTO toResponseDTO(Flight flight) {
        if (flight == null) return null;

        return FlightResponseDTO.builder()
                .id(flight.getId())
                .airlineId(flight.getAirline() != null ? flight.getAirline().getId() : null)
                .airlineName(flight.getAirline() != null ? flight.getAirline().getName() : null)
                .airlineCode(flight.getAirline() != null ? flight.getAirline().getCode() : null)
                .aircraftId(flight.getAircraft() != null ? flight.getAircraft().getId() : null)
                .aircraftModel(flight.getAircraft() != null ? flight.getAircraft().getModel() : null)
                .aircraftCapacity(flight.getAircraft() != null ? flight.getAircraft().getCapacity() : null)
                .routeId(flight.getRoute() != null ? flight.getRoute().getId() : null)
                .routeOrigin(flight.getRoute() != null ? flight.getRoute().getOrigin() : null)
                .routeDestination(flight.getRoute() != null ? flight.getRoute().getDestination() : null)
                .routeDurationMinutes(flight.getRoute() != null ? flight.getRoute().getDurationMinutes() : null)
                .departureDate(flight.getDepartureDate())
                .departureTime(flight.getDepartureTime())
                .price(flight.getPrice())
                .availableSeats(flight.getAvailableSeats())
                .status(flight.getStatus())
                .build();
    }
}
