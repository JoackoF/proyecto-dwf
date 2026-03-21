package udb.edu.sv.service;

import udb.edu.sv.dto.FlightDTO;

import java.util.List;
import java.util.Optional;

public interface FlightService {

    FlightDTO save(FlightDTO dto);

    List<FlightDTO> findAll();

    Optional<FlightDTO> findById(Long id);

    void deleteById(Long id);

    List<FlightDTO> searchFlights(String origin, String destination);
}