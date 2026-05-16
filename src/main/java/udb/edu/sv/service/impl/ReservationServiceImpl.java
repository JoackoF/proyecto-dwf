package udb.edu.sv.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import udb.edu.sv.dto.BookingRequestDTO;
import udb.edu.sv.dto.ReservationRequestDTO;
import udb.edu.sv.dto.ReservationResponseDTO;
import udb.edu.sv.entity.*;
import udb.edu.sv.entity.enums.ReservationStatus;
import udb.edu.sv.exception.BusinessException;
import udb.edu.sv.exception.DuplicateResourceException;
import udb.edu.sv.exception.ResourceNotFoundException;
import udb.edu.sv.mapper.ReservationMapper;
import udb.edu.sv.repository.*;
import udb.edu.sv.service.BookingService;
import udb.edu.sv.service.ReservationService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final FlightRepository flightRepository;
    private final PassengerRepository passengerRepository;
    private final UserRepository userRepository;
    private final ReservationMapper reservationMapper;
    private final BookingService bookingService;

    @Override
    @Transactional
    public ReservationResponseDTO save(ReservationRequestDTO dto) {

        Flight flight = flightRepository.findById(dto.getFlightId())
                .orElseThrow(() -> new ResourceNotFoundException("Flight", dto.getFlightId()));

        if (flight.getDepartureDate() != null && flight.getDepartureDate().isBefore(LocalDate.now())) {
            throw new BusinessException("No se puede reservar un vuelo cuya fecha de salida ya pasó");
        }

        if (flight.getAvailableSeats() <= 0) {
            throw new BusinessException("No hay asientos disponibles en el vuelo ID: " + flight.getId());
        }

        Passenger passenger = passengerRepository.findById(dto.getPassengerId())
                .orElseThrow(() -> new ResourceNotFoundException("Passenger", dto.getPassengerId()));

        if (reservationRepository.existsByFlightIdAndSeatNumber(dto.getFlightId(), dto.getSeatNumber())) {
            throw new DuplicateResourceException(
                    "El asiento " + dto.getSeatNumber() + " ya está reservado en el vuelo ID: " + flight.getId());
        }

        LocalDateTime now = LocalDateTime.now();

        Reservation reservation = new Reservation();
        reservation.setFlight(flight);
        reservation.setPassenger(passenger);
        reservation.setSeatNumber(dto.getSeatNumber());
        reservation.setStatus(ReservationStatus.PENDING);
        reservation.setReservationDate(now);

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User", dto.getUserId()));
            reservation.setUser(user);
        }

        flight.setAvailableSeats(flight.getAvailableSeats() - 1);
        flightRepository.save(flight);

        Reservation saved = reservationRepository.save(reservation);
        return reservationMapper.toResponseDTO(saved);
    }

    @Override
    public List<ReservationResponseDTO> findAll() {
        return reservationRepository.findAll()
                .stream()
                .map(reservationMapper::toResponseDTO)
                .toList();
    }

    @Override
    public Optional<ReservationResponseDTO> findById(Long id) {
        return reservationRepository.findById(id)
                .map(reservationMapper::toResponseDTO);
    }

    @Override
    public void deleteById(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reservation", id);
        }
        reservationRepository.deleteById(id);
    }

    @Override
    public void bookFlight(BookingRequestDTO request) {
        bookingService.bookFlight(request);
    }
}
