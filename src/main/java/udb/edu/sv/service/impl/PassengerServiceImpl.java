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
    public PassengerResponseDTO save(PassengerRequestDTO dto) {
        passengerRepository.findByPassportNumber(dto.getPassportNumber())
                .ifPresent(existing -> {
                    throw new DuplicateResourceException(
                            "Ya existe un pasajero con el pasaporte: " + dto.getPassportNumber());
                });

        Passenger passenger = passengerMapper.toEntity(dto);
        return passengerMapper.toResponseDTO(passengerRepository.save(passenger));
    }

    @Override
    public PassengerResponseDTO update(Long id, PassengerRequestDTO dto) {
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger", id));

        passengerRepository.findByPassportNumber(dto.getPassportNumber())
                .ifPresent(existing -> {
                    if (!existing.getId().equals(id)) {
                        throw new DuplicateResourceException(
                                "Otro pasajero ya usa el pasaporte: " + dto.getPassportNumber());
                    }
                });

        passenger.setFullName(dto.getFullName());
        passenger.setBirthDate(dto.getBirthDate());
        passenger.setPassportNumber(dto.getPassportNumber());

        return passengerMapper.toResponseDTO(passengerRepository.save(passenger));
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
