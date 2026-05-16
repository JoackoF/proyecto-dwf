package udb.edu.sv.service;

import udb.edu.sv.dto.PassengerRequestDTO;
import udb.edu.sv.dto.PassengerResponseDTO;

import java.util.List;
import java.util.Optional;

public interface PassengerService {

    PassengerResponseDTO save(PassengerRequestDTO passengerDTO);

    List<PassengerResponseDTO> findAll();

    Optional<PassengerResponseDTO> findById(Long id);

    void deleteById(Long id);
}
