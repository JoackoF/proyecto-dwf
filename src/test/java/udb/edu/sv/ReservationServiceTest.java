package udb.edu.sv;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import udb.edu.sv.dto.ReservationDTO;
import udb.edu.sv.entity.*;
import udb.edu.sv.repository.*;
import udb.edu.sv.service.ReservationService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class ReservationServiceTest {

    @Autowired private ReservationService reservationService;
    @Autowired private AirlineRepository airlineRepository;
    @Autowired private AircraftRepository aircraftRepository;
    @Autowired private RouteRepository routeRepository;
    @Autowired private FlightRepository flightRepository;
    @Autowired private PassengerRepository passengerRepository;

    @Test
    @DisplayName("Debe crear reservation correctamente")
    void shouldCreateReservation() {

        Airline airline = airlineRepository.save(
                Airline.builder().name("Test").code("T1").build()
        );

        Aircraft aircraft = aircraftRepository.save(
                Aircraft.builder().model("A320").capacity(100).airline(airline).build()
        );

        Route route = routeRepository.save(
                Route.builder().origin("A").destination("B").durationMinutes(60).build()
        );

        Flight flight = flightRepository.save(
                Flight.builder()
                        .airline(airline)
                        .aircraft(aircraft)
                        .route(route)
                        .departureDate(LocalDate.now().plusDays(1))
                        .departureTime(LocalTime.NOON)
                        .price(BigDecimal.valueOf(200))
                        .availableSeats(10)
                        .build()
        );

        Passenger passenger = passengerRepository.save(
                Passenger.builder()
                        .fullName("Tester")
                        .birthDate(LocalDate.of(2000,1,1))
                        .passportNumber("XYZ123")
                        .build()
        );

        ReservationDTO dto = ReservationDTO.builder()
                .flightId(flight.getId())
                .passengerId(passenger.getId())
                .seatNumber("5A")
                .build();

        ReservationDTO result = reservationService.save(dto);

        assertThat(result.getId()).isNotNull();
        assertThat(result.getSeatNumber()).isEqualTo("5A");
    }

    @Test
    @DisplayName("Debe fallar si el flight no existe")
    void shouldFailIfFlightNotFound() {

        ReservationDTO dto = ReservationDTO.builder()
                .flightId(999L)
                .passengerId(1L)
                .seatNumber("1A")
                .build();

        assertThatThrownBy(() -> reservationService.save(dto))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Flight");
    }
}