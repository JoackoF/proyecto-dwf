package udb.edu.sv.service;

import udb.edu.sv.entity.Flight;

import java.util.List;
import java.util.Optional;

public interface FlightService {

    Flight save(Flight flight);

    List<Flight> findAll();

    Optional<Flight> findById(Long id);

    void deleteById(Long id);
}