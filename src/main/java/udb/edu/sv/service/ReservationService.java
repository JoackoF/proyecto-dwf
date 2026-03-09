package udb.edu.sv.service;

import udb.edu.sv.dto.BookingRequestDTO;
import udb.edu.sv.dto.ReservationDTO;

import java.util.List;
import java.util.Optional;

public interface ReservationService {

    ReservationDTO save(ReservationDTO reservationDTO);

    List<ReservationDTO> findAll();

    Optional<ReservationDTO> findById(Long id);

    void deleteById(Long id);

    void bookFlight(BookingRequestDTO request);

}