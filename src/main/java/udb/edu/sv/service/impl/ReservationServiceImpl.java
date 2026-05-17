package udb.edu.sv.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import udb.edu.sv.dto.BookingRequestDTO;
import udb.edu.sv.dto.ReservationRequestDTO;
import udb.edu.sv.dto.ReservationResponseDTO;
import udb.edu.sv.entity.*;
import udb.edu.sv.entity.enums.ReservationStatus;
import udb.edu.sv.entity.enums.UserRole;
import udb.edu.sv.exception.BusinessException;
import udb.edu.sv.exception.DuplicateResourceException;
import udb.edu.sv.exception.ResourceNotFoundException;
import udb.edu.sv.mapper.ReservationMapper;
import udb.edu.sv.repository.*;
import udb.edu.sv.security.CurrentUser;
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
    private final ReservationMapper reservationMapper;
    private final BookingService bookingService;
    private final CurrentUser currentUser;

    @Override
    @Transactional
    public ReservationResponseDTO save(ReservationRequestDTO dto) {

        Flight flight = flightRepository.findById(dto.getFlightId())
                .orElseThrow(() -> new ResourceNotFoundException("Flight", dto.getFlightId()));

        if (flight.getStatus() != null && !flight.getStatus().allowsReservations()) {
            throw new BusinessException(
                    "El vuelo está en estado " + flight.getStatus() + " y no admite reservas");
        }

        if (flight.getDepartureDate() != null && flight.getDepartureDate().isBefore(LocalDate.now())) {
            throw new BusinessException("No se puede reservar un vuelo cuya fecha de salida ya pasó");
        }

        if (flight.getAvailableSeats() == null || flight.getAvailableSeats() <= 0) {
            throw new BusinessException("No hay asientos disponibles en el vuelo ID: " + flight.getId());
        }

        Passenger passenger = passengerRepository.findById(dto.getPassengerId())
                .orElseThrow(() -> new ResourceNotFoundException("Passenger", dto.getPassengerId()));

        if (reservationRepository.existsByFlightIdAndSeatNumber(dto.getFlightId(), dto.getSeatNumber())) {
            throw new DuplicateResourceException(
                    "El asiento " + dto.getSeatNumber() + " ya está reservado en el vuelo ID: " + flight.getId());
        }

        Reservation reservation = Reservation.builder()
                .flight(flight)
                .passenger(passenger)
                .user(currentUser.get().orElse(null))
                .seatNumber(dto.getSeatNumber())
                .status(ReservationStatus.PENDING)
                .reservationDate(LocalDateTime.now())
                .build();

        flight.setAvailableSeats(flight.getAvailableSeats() - 1);
        flightRepository.save(flight);

        return reservationMapper.toResponseDTO(reservationRepository.save(reservation));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponseDTO> findAll() {
        return reservationRepository.findAll()
                .stream()
                .map(reservationMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ReservationResponseDTO> findById(Long id) {
        return reservationRepository.findById(id)
                .map(reservationMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponseDTO> findByCurrentUser() {
        User user = currentUser.require();
        return reservationRepository.findByUserId(user.getId())
                .stream()
                .map(reservationMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public ReservationResponseDTO cancel(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", id));

        User actor = currentUser.require();
        boolean isAdmin = actor.getRole() == UserRole.ADMIN || actor.getRole() == UserRole.EMPLOYEE;
        boolean isOwner = reservation.getUser() != null && reservation.getUser().getId().equals(actor.getId());

        if (!isAdmin && !isOwner) {
            throw new BusinessException("No tienes permiso para cancelar esta reserva");
        }

        if (reservation.getStatus() == ReservationStatus.CANCELLED) {
            throw new BusinessException("La reserva ya está cancelada");
        }

        reservation.setStatus(ReservationStatus.CANCELLED);

        Flight flight = reservation.getFlight();
        if (flight != null && flight.getAvailableSeats() != null) {
            flight.setAvailableSeats(flight.getAvailableSeats() + 1);
            flightRepository.save(flight);
        }

        return reservationMapper.toResponseDTO(reservationRepository.save(reservation));
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
