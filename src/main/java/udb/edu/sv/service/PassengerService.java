package udb.edu.sv.service;

import udb.edu.sv.dto.PassengerDTO;

import java.util.List;
import java.util.Optional;

public interface PassengerService {

    PassengerDTO save(PassengerDTO passengerDTO);

    List<PassengerDTO> findAll();

    Optional<PassengerDTO> findById(Long id);

    void deleteById(Long id);
}