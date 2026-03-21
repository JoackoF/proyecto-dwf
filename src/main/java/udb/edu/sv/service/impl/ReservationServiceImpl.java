package udb.edu.sv.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import udb.edu.sv.dto.BookingRequestDTO;
import udb.edu.sv.dto.ReservationDTO;
import udb.edu.sv.entity.*;
import udb.edu.sv.entity.enums.ReservationStatus;
import udb.edu.sv.mapper.ReservationMapper;
import udb.edu.sv.repository.*;
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
    private final PaymentRepository paymentRepository;

    private final ReservationMapper reservationMapper;

    @Override
    public ReservationDTO save(ReservationDTO dto) {

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

        flight.setAvailableSeats(flight.getAvailableSeats() - 1);
        flightRepository.save(flight);

        Reservation saved = reservationRepository.save(reservation);

        return reservationMapper.toDTO(saved);
    }

    @Override
    public List<ReservationDTO> findAll() {

        return reservationRepository.findAll()
                .stream()
                .map(reservationMapper::toDTO)
                .toList();
    }

    @Override
    public Optional<ReservationDTO> findById(Long id) {

        return reservationRepository.findById(id)
                .map(reservationMapper::toDTO);
    }

    @Override
    public void deleteById(Long id) {

        reservationRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void bookFlight(BookingRequestDTO request) {

        Flight flight = flightRepository.findById(request.getFlightId())
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        if (flight.getAvailableSeats() <= 0) {
            throw new RuntimeException("No seats available");
        }

        boolean seatTaken = reservationRepository
                .existsByFlightIdAndSeatNumber(request.getFlightId(), request.getSeatNumber());

        if (seatTaken) {
            throw new RuntimeException("Seat already taken");
        }

        Passenger passenger = Passenger.builder()
                .fullName(request.getPassenger().getFullName())
                .passportNumber(request.getPassenger().getPassportNumber())
                .birthDate(request.getPassenger().getBirthDate())
                .build();

        passengerRepository.save(passenger);

        Reservation reservation = Reservation.builder()
                .flight(flight)
                .passenger(passenger)
                .seatNumber(request.getSeatNumber())
                .reservationDate(LocalDateTime.now())
                .status(ReservationStatus.CONFIRMED)
                .build();

        reservationRepository.save(reservation);

        Payment payment = Payment.builder()
                .reservation(reservation)
                .amount(flight.getPrice())
                .paymentType(request.getPaymentType())
                .paymentDate(LocalDateTime.now())
                .build();

        paymentRepository.save(payment);

        flight.setAvailableSeats(flight.getAvailableSeats() - 1);
        flightRepository.save(flight);
    }
}