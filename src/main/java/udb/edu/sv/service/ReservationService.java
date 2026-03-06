package udb.edu.sv.service;

import udb.edu.sv.entity.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationService {

    Reservation save(Reservation reservation);

    List<Reservation> findAll();

    Optional<Reservation> findById(Long id);

    void deleteById(Long id);
}