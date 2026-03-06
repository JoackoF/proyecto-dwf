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

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepositoryTests {

    @Autowired
    private AirlineRepository airlineRepository;

    @Autowired
    private AircraftRepository aircraftRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    @DisplayName("Test completo de persistencia del sistema")
    void fullRepositoryFlowTest() {

        // Crear Aerolínea
        Airline airline = Airline.builder()
                .name("Avianca")
                .code("AV")
                .build();

        Airline savedAirline = airlineRepository.save(airline);

        assertThat(savedAirline.getId()).isNotNull();


        // Crear Aircraft
        Aircraft aircraft = Aircraft.builder()
                .model("Airbus A320")
                .capacity(180)
                .airline(savedAirline)
                .build();

        Aircraft savedAircraft = aircraftRepository.save(aircraft);

        assertThat(savedAircraft.getId()).isNotNull();


        // Crear Route
        Route route = Route.builder()
                .origin("San Salvador")
                .destination("Madrid")
                .durationMinutes(600)
                .build();

        Route savedRoute = routeRepository.save(route);

        assertThat(savedRoute.getId()).isNotNull();


        // Crear Flight
        Flight flight = Flight.builder()
                .airline(savedAirline)
                .aircraft(savedAircraft)
                .route(savedRoute)
                .departureDate(LocalDate.now().plusDays(10))
                .departureTime(LocalTime.of(10, 30))
                .price(BigDecimal.valueOf(850))
                .availableSeats(150)
                .build();

        Flight savedFlight = flightRepository.save(flight);

        assertThat(savedFlight.getId()).isNotNull();


        // Crear Passenger
        Passenger passenger = Passenger.builder()
                .fullName("Juan Perez")
                .birthDate(LocalDate.of(1995, 5, 10))
                .passportNumber("A12345678")
                .build();

        Passenger savedPassenger = passengerRepository.save(passenger);

        assertThat(savedPassenger.getId()).isNotNull();


        // Crear Reservation
        Reservation reservation = Reservation.builder()
                .flight(savedFlight)
                .passenger(savedPassenger)
                .status(ReservationStatus.CONFIRMED)
                .seatNumber("12A")
                .build();

        Reservation savedReservation = reservationRepository.save(reservation);

        assertThat(savedReservation.getId()).isNotNull();


        // Crear Payment
        Payment payment = Payment.builder()
                .reservation(savedReservation)
                .amount(BigDecimal.valueOf(850))
                .paymentType(PaymentType.CARD)
                .build();

        Payment savedPayment = paymentRepository.save(payment);

        assertThat(savedPayment.getId()).isNotNull();


        // Verificar datos
        assertThat(airlineRepository.findAll()).isNotEmpty();
        assertThat(aircraftRepository.findAll()).isNotEmpty();
        assertThat(routeRepository.findAll()).isNotEmpty();
        assertThat(flightRepository.findAll()).isNotEmpty();
        assertThat(passengerRepository.findAll()).isNotEmpty();
        assertThat(reservationRepository.findAll()).isNotEmpty();
        assertThat(paymentRepository.findAll()).isNotEmpty();
    }
}