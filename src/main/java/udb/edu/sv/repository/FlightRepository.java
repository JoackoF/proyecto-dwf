package udb.edu.sv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import udb.edu.sv.entity.Flight;

import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    List<Flight> findByRouteOriginAndRouteDestination(String origin, String destination);

}