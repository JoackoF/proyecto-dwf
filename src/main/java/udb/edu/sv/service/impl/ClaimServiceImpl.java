package udb.edu.sv.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import udb.edu.sv.dto.ClaimRequestDTO;
import udb.edu.sv.dto.ClaimResponseDTO;
import udb.edu.sv.entity.Claim;
import udb.edu.sv.entity.Reservation;
import udb.edu.sv.entity.enums.ClaimStatus;
import udb.edu.sv.mapper.ClaimMapper;
import udb.edu.sv.repository.ClaimRepository;
import udb.edu.sv.repository.ReservationRepository;
import udb.edu.sv.service.ClaimService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClaimServiceImpl implements ClaimService {

    private final ClaimRepository claimRepository;
    private final ReservationRepository reservationRepository;
    private final ClaimMapper claimMapper;

    @Override
    @Transactional
    public ClaimResponseDTO save(ClaimRequestDTO dto) {
        Claim claim = claimMapper.toEntity(dto);

        if (dto.getReservationId() != null) {
            Reservation reservation = reservationRepository.findById(dto.getReservationId())
                    .orElseThrow(() -> new RuntimeException("Reservation not found"));
            claim.setReservation(reservation);
        }

        claim.setStatus(ClaimStatus.PENDING);
        claim.setCreatedAt(LocalDateTime.now());

        Claim saved = claimRepository.save(claim);
        return claimMapper.toResponseDTO(saved);
    }

    @Override
    public List<ClaimResponseDTO> findAll() {
        return claimRepository.findAll()
                .stream()
                .map(claimMapper::toResponseDTO)
                .toList();
    }

    @Override
    public Optional<ClaimResponseDTO> findById(Long id) {
        return claimRepository.findById(id)
                .map(claimMapper::toResponseDTO);
    }

    @Override
    public List<ClaimResponseDTO> findByReservationId(Long reservationId) {
        return claimRepository.findByReservationId(reservationId)
                .stream()
                .map(claimMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public ClaimResponseDTO resolve(Long id) {
        Claim claim = claimRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Claim not found"));
        claim.setStatus(ClaimStatus.RESOLVED);
        return claimMapper.toResponseDTO(claimRepository.save(claim));
    }

    @Override
    public void deleteById(Long id) {
        claimRepository.deleteById(id);
    }
}
