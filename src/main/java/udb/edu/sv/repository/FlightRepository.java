package udb.edu.sv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import udb.edu.sv.entity.Flight;

import java.time.LocalDate;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Query("""
        SELECT f FROM Flight f
        WHERE f.route.origin = :origin
        AND f.route.destination = :destination
        AND f.departureDate = :date
        AND f.availableSeats > 0
    """)
    List<Flight> searchFlights(String origin, String destination, LocalDate date);
}