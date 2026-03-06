package udb.edu.sv.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import udb.edu.sv.dto.FlightDTO;
import udb.edu.sv.entity.Flight;
import udb.edu.sv.mapper.FlightMapper;
import udb.edu.sv.repository.FlightRepository;
import udb.edu.sv.service.FlightService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final FlightMapper flightMapper;

    @Override
    public FlightDTO save(FlightDTO flightDTO) {

        Flight flight = flightMapper.toEntity(flightDTO);
        Flight saved = flightRepository.save(flight);

        return flightMapper.toDTO(saved);
    }

    @Override
    public List<FlightDTO> findAll() {
        return flightRepository.findAll()
                .stream()
                .map(flightMapper::toDTO)
                .toList();
    }

    @Override
    public Optional<FlightDTO> findById(Long id) {
        return flightRepository.findById(id)
                .map(flightMapper::toDTO);
    }

    @Override
    public void deleteById(Long id) {
        flightRepository.deleteById(id);
    }
}