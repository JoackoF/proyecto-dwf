package udb.edu.sv.service;

import udb.edu.sv.entity.Airline;

import java.util.List;
import java.util.Optional;

public interface AirlineService {

    Airline save(Airline airline);

    List<Airline> findAll();

    Optional<Airline> findById(Long id);

    void deleteById(Long id);
}