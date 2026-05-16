package udb.edu.sv.service;

import udb.edu.sv.dto.ClaimRequestDTO;
import udb.edu.sv.dto.ClaimResponseDTO;

import java.util.List;
import java.util.Optional;

public interface ClaimService {

    ClaimResponseDTO save(ClaimRequestDTO claimDTO);

    List<ClaimResponseDTO> findAll();

    Optional<ClaimResponseDTO> findById(Long id);

    List<ClaimResponseDTO> findByReservationId(Long reservationId);

    ClaimResponseDTO resolve(Long id);

    void deleteById(Long id);
}
