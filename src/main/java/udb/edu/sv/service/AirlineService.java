package udb.edu.sv.service;

import udb.edu.sv.dto.AirlineDTO;

import java.util.List;
import java.util.Optional;

public interface AirlineService {

    AirlineDTO save(AirlineDTO airlineDTO);

    List<AirlineDTO> findAll();

    Optional<AirlineDTO> findById(Long id);

    void deleteById(Long id);
}