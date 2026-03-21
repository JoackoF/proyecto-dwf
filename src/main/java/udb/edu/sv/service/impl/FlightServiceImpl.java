package udb.edu.sv.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import udb.edu.sv.dto.FlightDTO;
import udb.edu.sv.entity.*;
import udb.edu.sv.mapper.FlightMapper;
import udb.edu.sv.repository.*;
import udb.edu.sv.service.FlightService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final AirlineRepository airlineRepository;
    private final AircraftRepository aircraftRepository;
    private final RouteRepository routeRepository;
    private final FlightMapper flightMapper;

    @Override
    public FlightDTO save(FlightDTO dto) {

        Flight flight = flightMapper.toEntity(dto);

        if (dto.getAirlineId() != null) {
            Airline airline = airlineRepository.findById(dto.getAirlineId())
                    .orElseThrow(() -> new RuntimeException("Airline not found"));
            flight.setAirline(airline);
        }

        if (dto.getAircraftId() != null) {
            Aircraft aircraft = aircraftRepository.findById(dto.getAircraftId())
                    .orElseThrow(() -> new RuntimeException("Aircraft not found"));
            flight.setAircraft(aircraft);
        }

        if (dto.getRouteId() != null) {
            Route route = routeRepository.findById(dto.getRouteId())
                    .orElseThrow(() -> new RuntimeException("Route not found"));
            flight.setRoute(route);
        }

        Flight saved = flightRepository.save(flight);

        return flightMapper.toDTO(saved);
    }

    @Override
    public List<FlightDTO> findAll() {
        return flightRepository.findAll()
                .stream()
                .map(flightMapper::toDTO)
                .toList();
    }

    @Override
    public Optional<FlightDTO> findById(Long id) {
        return flightRepository.findById(id)
                .map(flightMapper::toDTO);
    }

    @Override
    public void deleteById(Long id) {
        flightRepository.deleteById(id);
    }

    @Override
    public List<FlightDTO> searchFlights(String origin, String destination) {
        return flightRepository
                .findByRouteOriginIgnoreCaseAndRouteDestinationIgnoreCase(origin, destination)
                .stream()
                .map(flightMapper::toDTO)
                .toList();
    }
}