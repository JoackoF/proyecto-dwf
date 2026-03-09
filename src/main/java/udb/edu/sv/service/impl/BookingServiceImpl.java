package udb.edu.sv.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import udb.edu.sv.dto.BookingRequestDTO;
import udb.edu.sv.entity.*;
import udb.edu.sv.entity.enums.ReservationStatus;
import udb.edu.sv.repository.*;
import udb.edu.sv.service.BookingService;

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
                                .orElseThrow(() -> new RuntimeException("Flight not found"));

                if (flight.getAvailableSeats() <= 0) {
                        throw new RuntimeException("No seats available");
                }

                Passenger passenger = passengerRepository
                                .findByPassportNumber(request.getPassenger().getPassportNumber())
                                .orElseGet(() -> {

                                        Passenger newPassenger = Passenger.builder()
                                                        .fullName(request.getPassenger().getFullName())
                                                        .passportNumber(request.getPassenger().getPassportNumber())
                                                        .birthDate(request.getPassenger().getBirthDate())
                                                        .build();

                                        return passengerRepository.save(newPassenger);
                                });

                boolean seatTaken = reservationRepository
                                .existsByFlightIdAndSeatNumber(flight.getId(), request.getSeatNumber());

                if (seatTaken) {
                        throw new RuntimeException("Seat already taken");
                }

                Reservation reservation = Reservation.builder()
                                .flight(flight)
                                .passenger(passenger)
                                .reservationDate(LocalDateTime.now())
                                .seatNumber(request.getSeatNumber())
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