package udb.edu.sv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import udb.edu.sv.entity.Reservation;
import udb.edu.sv.entity.enums.ReservationStatus;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    boolean existsByFlightIdAndSeatNumber(Long flightId, String seatNumber);

    List<Reservation> findByUserId(Long userId);

    List<Reservation> findByFlightIdAndStatusNot(Long flightId, ReservationStatus status);
}
