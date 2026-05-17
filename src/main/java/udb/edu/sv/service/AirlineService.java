package udb.edu.sv.service;

import udb.edu.sv.dto.AirlineRequestDTO;
import udb.edu.sv.dto.AirlineResponseDTO;

import java.util.List;
import java.util.Optional;

public interface AirlineService {

    AirlineResponseDTO save(AirlineRequestDTO airlineDTO);

    AirlineResponseDTO update(Long id, AirlineRequestDTO airlineDTO);

    List<AirlineResponseDTO> findAll();

    Optional<AirlineResponseDTO> findById(Long id);

    void deleteById(Long id);
}
