//Solo datos simples para probar que los JSON funcionen
package udb.edu.sv.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import udb.edu.sv.entity.*;
import udb.edu.sv.entity.enums.PaymentType;
import udb.edu.sv.entity.enums.ReservationStatus;
import udb.edu.sv.repository.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final AirlineRepository airlineRepository;
    private final AircraftRepository aircraftRepository;
    private final RouteRepository routeRepository;
    private final FlightRepository flightRepository;
    private final PassengerRepository passengerRepository;
    private final ReservationRepository reservationRepository;
    private final PaymentRepository paymentRepository;

    @Override
    public void run(String... args) {

        if (airlineRepository.count() > 0) {
            return;
        }

        // AIRLINE
        Airline airline = Airline.builder()
                .name("Avianca")
                .code("AV")
                .build();

        airlineRepository.save(airline);

        // AIRCRAFT
        Aircraft aircraft = Aircraft.builder()
                .model("Airbus A320")
                .capacity(180)
                .airline(airline)
                .build();

        aircraftRepository.save(aircraft);

        // ROUTE
        Route route = Route.builder()
                .origin("San Salvador")
                .destination("Madrid")
                .durationMinutes(600)
                .build();

        routeRepository.save(route);

        // FLIGHT
        Flight flight = Flight.builder()
                .airline(airline)
                .aircraft(aircraft)
                .route(route)
                .departureDate(LocalDate.now().plusDays(5))
                .departureTime(LocalTime.of(14, 30))
                .price(new BigDecimal("850.00"))
                .availableSeats(180)
                .build();

        flightRepository.save(flight);

        // PASSENGER
        Passenger passenger = Passenger.builder()
                .fullName("Joaquin Fuentes")
                .passportNumber("A12345678")
                .birthDate(LocalDate.of(1998, 5, 10))
                .build();

        passengerRepository.save(passenger);

        // RESERVATION
        Reservation reservation = Reservation.builder()
                .flight(flight)
                .passenger(passenger)
                .reservationDate(LocalDate.now().atStartOfDay())
                .seatNumber("12A")
                .status(ReservationStatus.CONFIRMED)
                .build();

        reservationRepository.save(reservation);

        // PAYMENT
        Payment payment = Payment.builder()
                .reservation(reservation)
                .amount(new BigDecimal("850.00"))
                .paymentType(PaymentType.CARD)
                .paymentDate(LocalDate.now().atStartOfDay())
                .build();

        paymentRepository.save(payment);

        System.out.println("DATOS INICIALES CARGADOS");
    }
}