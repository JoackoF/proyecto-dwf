package udb.edu.sv.service;

import udb.edu.sv.dto.BookingRequestDTO;
import udb.edu.sv.dto.ReservationRequestDTO;
import udb.edu.sv.dto.ReservationResponseDTO;

import java.util.List;
import java.util.Optional;

public interface ReservationService {

    ReservationResponseDTO save(ReservationRequestDTO reservationDTO);

    List<ReservationResponseDTO> findAll();

    Optional<ReservationResponseDTO> findById(Long id);

    List<ReservationResponseDTO> findByCurrentUser();

    ReservationResponseDTO cancel(Long id);

    void deleteById(Long id);

    void bookFlight(BookingRequestDTO request);
}
