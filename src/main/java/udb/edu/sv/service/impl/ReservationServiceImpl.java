package udb.edu.sv.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import udb.edu.sv.dto.BookingRequestDTO;
import udb.edu.sv.dto.ReservationRequestDTO;
import udb.edu.sv.dto.ReservationResponseDTO;
import udb.edu.sv.entity.*;
import udb.edu.sv.entity.enums.ReservationStatus;
import udb.edu.sv.mapper.ReservationMapper;
import udb.edu.sv.repository.*;
import udb.edu.sv.service.BookingService;
import udb.edu.sv.service.ReservationService;

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
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        if (flight.getAvailableSeats() <= 0) {
            throw new RuntimeException("No seats available");
        }

        Passenger passenger = passengerRepository.findById(dto.getPassengerId())
                .orElseThrow(() -> new RuntimeException("Passenger not found"));

        boolean seatTaken = reservationRepository
                .existsByFlightIdAndSeatNumber(dto.getFlightId(), dto.getSeatNumber());

        if (seatTaken) {
            throw new RuntimeException("Seat already taken");
        }

        Reservation reservation = new Reservation();
        reservation.setFlight(flight);
        reservation.setPassenger(passenger);
        reservation.setSeatNumber(dto.getSeatNumber());
        reservation.setStatus(ReservationStatus.PENDING);
        reservation.setReservationDate(LocalDateTime.now());

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
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
        reservationRepository.deleteById(id);
    }

    @Override
    public void bookFlight(BookingRequestDTO request) {
        bookingService.bookFlight(request);
    }
}
