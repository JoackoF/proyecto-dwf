package udb.edu.sv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import udb.edu.sv.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    boolean existsByFlightIdAndSeatNumber(Long flightId, String seatNumber);

}