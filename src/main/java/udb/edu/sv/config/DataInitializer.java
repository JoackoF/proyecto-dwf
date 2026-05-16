package udb.edu.sv.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import udb.edu.sv.entity.*;
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
        printCredentials();
    }

    private void seedUsers() {
        if (userRepository.count() > 0) {
            log.info("[seed] users table not empty, skipping user seed");
            return;
        }

        User admin = User.builder()
                .fullName("Administrador del Sistema")
                .email(ADMIN_EMAIL)
                .password(passwordEncoder.encode(ADMIN_PASSWORD))
                .role(UserRole.ADMIN)
                .createdAt(LocalDateTime.now())
                .build();

        User customer = User.builder()
                .fullName("Cliente Demo")
                .email(CUSTOMER_EMAIL)
                .password(passwordEncoder.encode(CUSTOMER_PASSWORD))
                .role(UserRole.CUSTOMER)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(admin);
        userRepository.save(customer);
        log.info("[seed] inserted 2 users (ADMIN + CUSTOMER)");
    }

    private void seedSupportData() {
        if (airlineRepository.count() > 0) {
            log.info("[seed] airlines table not empty, skipping support-data seed");
            return;
        }

        Airline airline = airlineRepository.save(Airline.builder()
                .name("Avianca")
                .code("AV")
                .build());

        Airline copa = airlineRepository.save(Airline.builder()
                .name("Copa Airlines")
                .code("CM")
                .build());

        Aircraft a320 = aircraftRepository.save(Aircraft.builder()
                .model("Airbus A320")
                .capacity(180)
                .airline(airline)
                .build());

        Aircraft b737 = aircraftRepository.save(Aircraft.builder()
                .model("Boeing 737-800")
                .capacity(160)
                .airline(copa)
                .build());

        Route sssMad = routeRepository.save(Route.builder()
                .origin("San Salvador")
                .destination("Madrid")
                .durationMinutes(600)
                .build());

        Route sssPty = routeRepository.save(Route.builder()
                .origin("San Salvador")
                .destination("Panamá")
                .durationMinutes(120)
                .build());

        flightRepository.save(Flight.builder()
                .airline(airline)
                .aircraft(a320)
                .route(sssMad)
                .departureDate(LocalDate.now().plusDays(5))
                .departureTime(LocalTime.of(14, 30))
                .price(new BigDecimal("850.00"))
                .availableSeats(180)
                .build());

        flightRepository.save(Flight.builder()
                .airline(copa)
                .aircraft(b737)
                .route(sssPty)
                .departureDate(LocalDate.now().plusDays(2))
                .departureTime(LocalTime.of(8, 15))
                .price(new BigDecimal("220.00"))
                .availableSeats(160)
                .build());

        log.info("[seed] inserted 2 airlines, 2 aircraft, 2 routes, 2 flights");
    }

    private void printCredentials() {
        String line = "=".repeat(72);
        log.info(line);
        log.info("  CREDENCIALES DE PRUEBA");
        log.info(line);
        log.info("  ADMIN     -> email: {}  | password: {}", ADMIN_EMAIL, ADMIN_PASSWORD);
        log.info("  CUSTOMER  -> email: {}  | password: {}", CUSTOMER_EMAIL, CUSTOMER_PASSWORD);
        log.info(line);
        log.info("  POST /api/auth/login con email + password para obtener el JWT");
        log.info(line);
    }
}
