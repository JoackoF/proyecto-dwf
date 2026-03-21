package udb.edu.sv;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import udb.edu.sv.entity.*;
import udb.edu.sv.entity.enums.*;
import udb.edu.sv.repository.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepositoryTests {

    @Autowired private AirlineRepository airlineRepository;
    @Autowired private AircraftRepository aircraftRepository;
    @Autowired private RouteRepository routeRepository;
    @Autowired private FlightRepository flightRepository;
    @Autowired private PassengerRepository passengerRepository;
    @Autowired private ReservationRepository reservationRepository;
    @Autowired private PaymentRepository paymentRepository;

    @Test
    @DisplayName("Test completo sin conflictos de datos")
    void fullRepositoryFlowTest() {

        String uniqueCode = "AV-" + UUID.randomUUID();

        Airline airline = Airline.builder()
                .name("Avianca Test")
                .code(uniqueCode)
                .build();

        Airline savedAirline = airlineRepository.save(airline);

        Aircraft aircraft = Aircraft.builder()
                .model("A320 Test")
                .capacity(180)
                .airline(savedAirline)
                .build();

        Aircraft savedAircraft = aircraftRepository.save(aircraft);

        Route route = Route.builder()
                .origin("SS")
                .destination("MAD")
                .durationMinutes(600)
                .build();

        Route savedRoute = routeRepository.save(route);

        Flight flight = Flight.builder()
                .airline(savedAirline)
                .aircraft(savedAircraft)
                .route(savedRoute)
                .departureDate(LocalDate.now().plusDays(5))
                .departureTime(LocalTime.of(10, 0))
                .price(BigDecimal.valueOf(500))
                .availableSeats(100)
                .build();

        Flight savedFlight = flightRepository.save(flight);

        Passenger passenger = Passenger.builder()
                .fullName("Test User")
                .birthDate(LocalDate.of(2000, 1, 1))
                .passportNumber("P-" + UUID.randomUUID())
                .build();

        Passenger savedPassenger = passengerRepository.save(passenger);

        Reservation reservation = Reservation.builder()
                .flight(savedFlight)
                .passenger(savedPassenger)
                .status(ReservationStatus.CONFIRMED)
                .seatNumber("1A")
                .build();

        Reservation savedReservation = reservationRepository.save(reservation);

        Payment payment = Payment.builder()
                .reservation(savedReservation)
                .amount(BigDecimal.valueOf(500))
                .paymentType(PaymentType.CARD)
                .build();

        Payment savedPayment = paymentRepository.save(payment);

        assertThat(savedPayment.getId()).isNotNull();
    }
}