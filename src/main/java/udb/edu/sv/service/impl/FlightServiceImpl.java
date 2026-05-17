package udb.edu.sv.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import udb.edu.sv.dto.FlightRequestDTO;
import udb.edu.sv.dto.FlightResponseDTO;
import udb.edu.sv.entity.*;
import udb.edu.sv.entity.enums.FlightStatus;
import udb.edu.sv.entity.enums.ReservationStatus;
import udb.edu.sv.exception.BusinessException;
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
    private final ReservationRepository reservationRepository;
    private final FlightMapper flightMapper;

    @Override
    @Transactional
    public FlightResponseDTO save(FlightRequestDTO dto) {
        Airline airline = airlineRepository.findById(dto.getAirlineId())
                .orElseThrow(() -> new ResourceNotFoundException("Airline", dto.getAirlineId()));

        Aircraft aircraft = aircraftRepository.findById(dto.getAircraftId())
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft", dto.getAircraftId()));

        Route route = routeRepository.findById(dto.getRouteId())
                .orElseThrow(() -> new ResourceNotFoundException("Route", dto.getRouteId()));

        if (aircraft.getAirline() != null && !aircraft.getAirline().getId().equals(airline.getId())) {
            throw new BusinessException(
                    "La aeronave ID " + aircraft.getId() + " no pertenece a la aerolínea ID " + airline.getId());
        }

        Integer seats = dto.getAvailableSeats();
        if (seats == null) {
            seats = aircraft.getCapacity();
        } else if (seats > aircraft.getCapacity()) {
            throw new BusinessException(
                    "Los asientos disponibles (" + seats + ") superan la capacidad de la aeronave ("
                            + aircraft.getCapacity() + ")");
        }

        Flight flight = Flight.builder()
                .airline(airline)
                .aircraft(aircraft)
                .route(route)
                .departureDate(dto.getDepartureDate())
                .departureTime(dto.getDepartureTime())
                .price(dto.getPrice())
                .availableSeats(seats)
                .status(dto.getStatus() != null ? dto.getStatus() : FlightStatus.SCHEDULED)
                .build();

        return flightMapper.toResponseDTO(flightRepository.save(flight));
    }

    @Override
    @Transactional
    public FlightResponseDTO update(Long id, FlightRequestDTO dto) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight", id));

        Airline airline = airlineRepository.findById(dto.getAirlineId())
                .orElseThrow(() -> new ResourceNotFoundException("Airline", dto.getAirlineId()));

        Aircraft aircraft = aircraftRepository.findById(dto.getAircraftId())
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft", dto.getAircraftId()));

        Route route = routeRepository.findById(dto.getRouteId())
                .orElseThrow(() -> new ResourceNotFoundException("Route", dto.getRouteId()));

        if (aircraft.getAirline() != null && !aircraft.getAirline().getId().equals(airline.getId())) {
            throw new BusinessException(
                    "La aeronave ID " + aircraft.getId() + " no pertenece a la aerolínea ID " + airline.getId());
        }

        Integer seats = dto.getAvailableSeats() != null ? dto.getAvailableSeats() : flight.getAvailableSeats();
        if (seats != null && seats > aircraft.getCapacity()) {
            throw new BusinessException(
                    "Los asientos disponibles (" + seats + ") superan la capacidad de la aeronave ("
                            + aircraft.getCapacity() + ")");
        }

        flight.setAirline(airline);
        flight.setAircraft(aircraft);
        flight.setRoute(route);
        flight.setDepartureDate(dto.getDepartureDate());
        flight.setDepartureTime(dto.getDepartureTime());
        flight.setPrice(dto.getPrice());
        flight.setAvailableSeats(seats);
        if (dto.getStatus() != null) {
            flight.setStatus(dto.getStatus());
        }

        return flightMapper.toResponseDTO(flightRepository.save(flight));
    }

    @Override
    @Transactional
    public FlightResponseDTO changeStatus(Long id, FlightStatus newStatus) {
        if (newStatus == null) {
            throw new BusinessException("El nuevo estado es obligatorio");
        }
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight", id));

        FlightStatus current = flight.getStatus() != null ? flight.getStatus() : FlightStatus.SCHEDULED;

        if (current == FlightStatus.ARRIVED || current == FlightStatus.CANCELLED) {
            throw new BusinessException(
                    "No se puede cambiar el estado de un vuelo " + current.name());
        }

        flight.setStatus(newStatus);
        return flightMapper.toResponseDTO(flightRepository.save(flight));
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

    @Override
    public List<String> findTakenSeats(Long flightId) {
        if (!flightRepository.existsById(flightId)) {
            throw new ResourceNotFoundException("Flight", flightId);
        }
        return reservationRepository
                .findByFlightIdAndStatusNot(flightId, ReservationStatus.CANCELLED)
                .stream()
                .map(Reservation::getSeatNumber)
                .filter(s -> s != null && !s.isBlank())
                .toList();
    }
}
