package udb.edu.sv.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import udb.edu.sv.dto.PassengerDTO;
import udb.edu.sv.entity.Passenger;
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
    public PassengerDTO save(PassengerDTO passengerDTO) {

        Passenger passenger = passengerMapper.toEntity(passengerDTO);
        Passenger saved = passengerRepository.save(passenger);

        return passengerMapper.toDTO(saved);
    }

    @Override
    public List<PassengerDTO> findAll() {
        return passengerRepository.findAll()
                .stream()
                .map(passengerMapper::toDTO)
                .toList();
    }

    @Override
    public Optional<PassengerDTO> findById(Long id) {
        return passengerRepository.findById(id)
                .map(passengerMapper::toDTO);
    }

    @Override
    public void deleteById(Long id) {
        passengerRepository.deleteById(id);
    }
}