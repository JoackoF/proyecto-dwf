package udb.edu.sv.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import udb.edu.sv.dto.FlightRequestDTO;
import udb.edu.sv.dto.FlightResponseDTO;
import udb.edu.sv.entity.*;
import udb.edu.sv.exception.ResourceNotFoundException;
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
    public FlightResponseDTO save(FlightRequestDTO dto) {
        Flight flight = flightMapper.toEntity(dto);

        Airline airline = airlineRepository.findById(dto.getAirlineId())
                .orElseThrow(() -> new ResourceNotFoundException("Airline", dto.getAirlineId()));
        flight.setAirline(airline);

        Aircraft aircraft = aircraftRepository.findById(dto.getAircraftId())
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft", dto.getAircraftId()));
        flight.setAircraft(aircraft);

        Route route = routeRepository.findById(dto.getRouteId())
                .orElseThrow(() -> new ResourceNotFoundException("Route", dto.getRouteId()));
        flight.setRoute(route);

        Flight saved = flightRepository.save(flight);
        return flightMapper.toResponseDTO(saved);
    }

    @Override
    public List<FlightResponseDTO> findAll() {
        return flightRepository.findAll()
                .stream()
                .map(flightMapper::toResponseDTO)
                .toList();
    }

    @Override
    public Optional<FlightResponseDTO> findById(Long id) {
        return flightRepository.findById(id)
                .map(flightMapper::toResponseDTO);
    }

    @Override
    public void deleteById(Long id) {
        if (!flightRepository.existsById(id)) {
            throw new ResourceNotFoundException("Flight", id);
        }
        flightRepository.deleteById(id);
    }

    @Override
    public List<FlightResponseDTO> searchFlights(String origin, String destination) {
        return flightRepository
                .findByRouteOriginIgnoreCaseAndRouteDestinationIgnoreCase(origin, destination)
                .stream()
                .map(flightMapper::toResponseDTO)
                .toList();
    }
}
