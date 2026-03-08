package udb.edu.sv.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import udb.edu.sv.dto.ReservationDTO;
import udb.edu.sv.entity.Reservation;
import udb.edu.sv.mapper.ReservationMapper;
import udb.edu.sv.repository.ReservationRepository;
import udb.edu.sv.service.ReservationService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    @Override
    public ReservationDTO save(ReservationDTO reservationDTO) {

        Reservation reservation = reservationMapper.toEntity(reservationDTO);
        Reservation saved = reservationRepository.save(reservation);

        return reservationMapper.toDTO(saved);
    }

    @Override
    public List<ReservationDTO> findAll() {

        return reservationRepository.findAll()
                .stream()
                .map(reservationMapper::toDTO)
                .toList();
    }

    @Override
    public Optional<ReservationDTO> findById(Long id) {

        return reservationRepository.findById(id)
                .map(reservationMapper::toDTO);
    }

    @Override
    public void deleteById(Long id) {

        reservationRepository.deleteById(id);
    }
}