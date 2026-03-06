package udb.edu.sv.service;

import udb.edu.sv.dto.AircraftDTO;

import java.util.List;
import java.util.Optional;

public interface AircraftService {

    AircraftDTO save(AircraftDTO aircraftDTO);

    List<AircraftDTO> findAll();

    Optional<AircraftDTO> findById(Long id);

    void deleteById(Long id);
}