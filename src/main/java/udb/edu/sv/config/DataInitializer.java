package udb.edu.sv.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import udb.edu.sv.entity.*;
import udb.edu.sv.entity.enums.FlightStatus;
import udb.edu.sv.entity.enums.UserRole;
import udb.edu.sv.repository.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private static final String ADMIN_EMAIL = "admin@aerolinea.sv";
    private static final String ADMIN_PASSWORD = "Admin123!";
    private static final String CUSTOMER_EMAIL = "cliente@aerolinea.sv";
    private static final String CUSTOMER_PASSWORD = "Cliente123!";
    private static final String EMPLOYEE_EMAIL = "empleado@aerolinea.sv";
    private static final String EMPLOYEE_PASSWORD = "Empleado123!";

    private final UserRepository userRepository;
    private final AirlineRepository airlineRepository;
    private final AircraftRepository aircraftRepository;
    private final RouteRepository routeRepository;
    private final FlightRepository flightRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        seedUsers();
        seedSupportData();
        backfillFlightStatus();
        printCredentials();
    }

    private void seedUsers() {
        int inserted = 0;
        inserted += createUserIfMissing(ADMIN_EMAIL, "Administrador del Sistema", ADMIN_PASSWORD, UserRole.ADMIN);
        inserted += createUserIfMissing(CUSTOMER_EMAIL, "Cliente Demo", CUSTOMER_PASSWORD, UserRole.CUSTOMER);
        inserted += createUserIfMissing(EMPLOYEE_EMAIL, "Empleado Demo", EMPLOYEE_PASSWORD, UserRole.EMPLOYEE);
        log.info("[seed] inserted {} demo users", inserted);
    }

    private int createUserIfMissing(String email, String fullName, String password, UserRole role) {
        if (userRepository.existsByEmail(email)) {
            log.info("[seed] user {} already exists, skipping", email);
            return 0;
        }
        userRepository.save(User.builder()
                .fullName(fullName)
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(role)
                .createdAt(LocalDateTime.now())
                .build());
        return 1;
    }

    private void seedSupportData() {
        if (airlineRepository.count() > 0) {
            log.info("[seed] airlines table not empty, skipping support-data seed");
            return;
        }

        Airline avianca = airlineRepository.save(Airline.builder().name("Avianca").code("AV").build());
        Airline copa = airlineRepository.save(Airline.builder().name("Copa Airlines").code("CM").build());
        Airline aa = airlineRepository.save(Airline.builder().name("American Airlines").code("AA").build());

        Aircraft a320 = aircraftRepository.save(Aircraft.builder()
                .model("Airbus A320").capacity(180).airline(avianca).build());
        Aircraft a319 = aircraftRepository.save(Aircraft.builder()
                .model("Airbus A319").capacity(140).airline(avianca).build());
        Aircraft b737 = aircraftRepository.save(Aircraft.builder()
                .model("Boeing 737-800").capacity(160).airline(copa).build());
        Aircraft b777 = aircraftRepository.save(Aircraft.builder()
                .model("Boeing 777-300").capacity(396).airline(aa).build());

        Route sssMad = routeRepository.save(Route.builder()
                .origin("San Salvador").destination("Madrid").durationMinutes(600).build());
        Route sssPty = routeRepository.save(Route.builder()
                .origin("San Salvador").destination("Panamá").durationMinutes(120).build());
        Route sssMia = routeRepository.save(Route.builder()
                .origin("San Salvador").destination("Miami").durationMinutes(180).build());
        Route ptyBog = routeRepository.save(Route.builder()
                .origin("Panamá").destination("Bogotá").durationMinutes(90).build());

        flightRepository.save(Flight.builder()
                .airline(avianca).aircraft(a320).route(sssMad)
                .departureDate(LocalDate.now().plusDays(7))
                .departureTime(LocalTime.of(14, 30))
                .price(new BigDecimal("850.00"))
                .availableSeats(180)
                .status(FlightStatus.SCHEDULED)
                .build());

        flightRepository.save(Flight.builder()
                .airline(avianca).aircraft(a319).route(sssMia)
                .departureDate(LocalDate.now().plusDays(3))
                .departureTime(LocalTime.of(9, 0))
                .price(new BigDecimal("320.00"))
                .availableSeats(140)
                .status(FlightStatus.SCHEDULED)
                .build());

        flightRepository.save(Flight.builder()
                .airline(copa).aircraft(b737).route(sssPty)
                .departureDate(LocalDate.now().plusDays(2))
                .departureTime(LocalTime.of(8, 15))
                .price(new BigDecimal("220.00"))
                .availableSeats(160)
                .status(FlightStatus.SCHEDULED)
                .build());

        flightRepository.save(Flight.builder()
                .airline(copa).aircraft(b737).route(ptyBog)
                .departureDate(LocalDate.now().plusDays(1))
                .departureTime(LocalTime.of(16, 45))
                .price(new BigDecimal("180.00"))
                .availableSeats(155)
                .status(FlightStatus.DELAYED)
                .build());

        flightRepository.save(Flight.builder()
                .airline(aa).aircraft(b777).route(sssMia)
                .departureDate(LocalDate.now().plusDays(10))
                .departureTime(LocalTime.of(11, 0))
                .price(new BigDecimal("420.00"))
                .availableSeats(396)
                .status(FlightStatus.SCHEDULED)
                .build());

        flightRepository.save(Flight.builder()
                .airline(avianca).aircraft(a320).route(sssMad)
                .departureDate(LocalDate.now().plusDays(20))
                .departureTime(LocalTime.of(20, 0))
                .price(new BigDecimal("950.00"))
                .availableSeats(180)
                .status(FlightStatus.SCHEDULED)
                .build());

        log.info("[seed] inserted 3 airlines, 4 aircraft, 4 routes, 6 flights");
    }

    private void backfillFlightStatus() {
        int updated = 0;
        for (Flight f : flightRepository.findAll()) {
            if (f.getStatus() == null) {
                f.setStatus(FlightStatus.SCHEDULED);
                flightRepository.save(f);
                updated++;
            }
        }
        if (updated > 0) {
            log.info("[seed] backfilled status=SCHEDULED on {} legacy flights", updated);
        }
    }

    private void printCredentials() {
        String line = "=".repeat(72);
        log.info(line);
        log.info("  CREDENCIALES DE PRUEBA");
        log.info(line);
        log.info("  ADMIN     -> email: {}  | password: {}", ADMIN_EMAIL, ADMIN_PASSWORD);
        log.info("  EMPLOYEE  -> email: {}  | password: {}", EMPLOYEE_EMAIL, EMPLOYEE_PASSWORD);
        log.info("  CUSTOMER  -> email: {}  | password: {}", CUSTOMER_EMAIL, CUSTOMER_PASSWORD);
        log.info(line);
        log.info("  POST /api/auth/login con email + password para obtener el JWT");
        log.info(line);
    }
}
