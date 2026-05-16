package udb.edu.sv.service;

import udb.edu.sv.dto.AircraftRequestDTO;
import udb.edu.sv.dto.AircraftResponseDTO;

import java.util.List;
import java.util.Optional;

public interface AircraftService {

    AircraftResponseDTO save(AircraftRequestDTO aircraftDTO);

    List<AircraftResponseDTO> findAll();

    Optional<AircraftResponseDTO> findById(Long id);

    void deleteById(Long id);
}
