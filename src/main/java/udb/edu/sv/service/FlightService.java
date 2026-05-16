package udb.edu.sv.service;

import udb.edu.sv.dto.FlightRequestDTO;
import udb.edu.sv.dto.FlightResponseDTO;

import java.util.List;
import java.util.Optional;

public interface FlightService {

    FlightResponseDTO save(FlightRequestDTO dto);

    List<FlightResponseDTO> findAll();

    Optional<FlightResponseDTO> findById(Long id);

    void deleteById(Long id);

    List<FlightResponseDTO> searchFlights(String origin, String destination);
}
