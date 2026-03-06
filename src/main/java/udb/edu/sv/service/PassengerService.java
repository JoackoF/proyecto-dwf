package udb.edu.sv.service;

import udb.edu.sv.entity.Passenger;

import java.util.List;
import java.util.Optional;

public interface PassengerService {

    Passenger save(Passenger passenger);

    List<Passenger> findAll();

    Optional<Passenger> findById(Long id);

    void deleteById(Long id);
}