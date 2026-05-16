package udb.edu.sv.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import udb.edu.sv.dto.BookingRequestDTO;
import udb.edu.sv.entity.*;
import udb.edu.sv.entity.enums.ReservationStatus;
import udb.edu.sv.exception.BusinessException;
import udb.edu.sv.exception.DuplicateResourceException;
import udb.edu.sv.exception.ResourceNotFoundException;
import udb.edu.sv.repository.*;
import udb.edu.sv.service.BookingService;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final FlightRepository flightRepository;
    private final PassengerRepository passengerRepository;
    private final ReservationRepository reservationRepository;
    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public void bookFlight(BookingRequestDTO request) {

        Flight flight = flightRepository.findById(request.getFlightId())
                .orElseThrow(() -> new ResourceNotFoundException("Flight", request.getFlightId()));

        if (flight.getDepartureDate() != null && flight.getDepartureDate().isBefore(LocalDate.now())) {
            throw new BusinessException("No se puede reservar un vuelo cuya fecha de salida ya pasó");
        }

        if (flight.getAvailableSeats() <= 0) {
            throw new BusinessException("No hay asientos disponibles en el vuelo ID: " + flight.getId());
        }

        Passenger passenger = passengerRepository
                .findByPassportNumber(request.getPassenger().getPassportNumber())
                .orElseGet(() -> passengerRepository.save(
                        Passenger.builder()
                                .fullName(request.getPassenger().getFullName())
                                .passportNumber(request.getPassenger().getPassportNumber())
                                .birthDate(request.getPassenger().getBirthDate())
                                .build()
                ));

        if (reservationRepository.existsByFlightIdAndSeatNumber(flight.getId(), request.getSeatNumber())) {
            throw new DuplicateResourceException(
                    "El asiento " + request.getSeatNumber() + " ya está reservado en el vuelo ID: " + flight.getId());
        }

        LocalDateTime now = LocalDateTime.now();

        Reservation reservation = Reservation.builder()
                .flight(flight)
                .passenger(passenger)
                .reservationDate(now)
                .seatNumber(request.getSeatNumber())
                .status(ReservationStatus.CONFIRMED)
                .build();

        reservationRepository.save(reservation);

        Payment payment = Payment.builder()
                .reservation(reservation)
                .amount(flight.getPrice())
                .paymentType(request.getPaymentType())
                .paymentDate(now)
                .build();

        paymentRepository.save(payment);

        flight.setAvailableSeats(flight.getAvailableSeats() - 1);
        flightRepository.save(flight);
    }
}
