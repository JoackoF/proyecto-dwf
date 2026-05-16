package udb.edu.sv.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import udb.edu.sv.dto.PassengerRequestDTO;
import udb.edu.sv.dto.PassengerResponseDTO;
import udb.edu.sv.entity.Passenger;
import udb.edu.sv.exception.DuplicateResourceException;
import udb.edu.sv.exception.ResourceNotFoundException;
import udb.edu.sv.mapper.PassengerMapper;
import udb.edu.sv.repository.PassengerRepository;
import udb.edu.sv.service.PassengerService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;

    @Override
    public PassengerResponseDTO save(PassengerRequestDTO passengerDTO) {
        passengerRepository.findByPassportNumber(passengerDTO.getPassportNumber())
                .ifPresent(existing -> {
                    throw new DuplicateResourceException(
                            "Ya existe un pasajero con el pasaporte: " + passengerDTO.getPassportNumber());
                });

        Passenger passenger = passengerMapper.toEntity(passengerDTO);
        Passenger saved = passengerRepository.save(passenger);
        return passengerMapper.toResponseDTO(saved);
    }

    @Override
    public List<PassengerResponseDTO> findAll() {
        return passengerRepository.findAll()
                .stream()
                .map(passengerMapper::toResponseDTO)
                .toList();
    }

    @Override
    public Optional<PassengerResponseDTO> findById(Long id) {
        return passengerRepository.findById(id)
                .map(passengerMapper::toResponseDTO);
    }

    @Override
    public void deleteById(Long id) {
        if (!passengerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Passenger", id);
        }
        passengerRepository.deleteById(id);
    }
}
