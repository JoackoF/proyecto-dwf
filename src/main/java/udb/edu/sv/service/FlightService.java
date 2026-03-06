package udb.edu.sv.service;

import udb.edu.sv.dto.FlightDTO;

import java.util.List;
import java.util.Optional;

public interface FlightService {

    FlightDTO save(FlightDTO flightDTO);

    List<FlightDTO> findAll();

    Optional<FlightDTO> findById(Long id);

    void deleteById(Long id);
}