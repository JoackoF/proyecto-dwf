package udb.edu.sv.service.impl;

import org.springframework.stereotype.Service;
import udb.edu.sv.entity.Reservation;
import udb.edu.sv.repository.ReservationRepository;
import udb.edu.sv.service.ReservationService;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Reservation save(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return reservationRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        reservationRepository.deleteById(id);
    }
}