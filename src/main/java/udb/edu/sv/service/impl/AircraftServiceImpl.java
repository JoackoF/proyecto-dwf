package udb.edu.sv.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import udb.edu.sv.dto.AircraftRequestDTO;
import udb.edu.sv.dto.AircraftResponseDTO;
import udb.edu.sv.entity.Aircraft;
import udb.edu.sv.entity.Airline;
import udb.edu.sv.exception.ResourceNotFoundException;
import udb.edu.sv.mapper.AircraftMapper;
import udb.edu.sv.repository.AircraftRepository;
import udb.edu.sv.repository.AirlineRepository;
import udb.edu.sv.service.AircraftService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AircraftServiceImpl implements AircraftService {

    private final AircraftRepository aircraftRepository;
    private final AirlineRepository airlineRepository;
    private final AircraftMapper aircraftMapper;

    @Override
    public AircraftResponseDTO save(AircraftRequestDTO dto) {
        Aircraft aircraft = aircraftMapper.toEntity(dto);

        Airline airline = airlineRepository.findById(dto.getAirlineId())
                .orElseThrow(() -> new ResourceNotFoundException("Airline", dto.getAirlineId()));
        aircraft.setAirline(airline);

        Aircraft saved = aircraftRepository.save(aircraft);
        return aircraftMapper.toResponseDTO(saved);
    }

    @Override
    public List<AircraftResponseDTO> findAll() {
        return aircraftRepository.findAll()
                .stream()
                .map(aircraftMapper::toResponseDTO)
                .toList();
    }

    @Override
    public Optional<AircraftResponseDTO> findById(Long id) {
        return aircraftRepository.findById(id)
                .map(aircraftMapper::toResponseDTO);
    }

    @Override
    public void deleteById(Long id) {
        if (!aircraftRepository.existsById(id)) {
            throw new ResourceNotFoundException("Aircraft", id);
        }
        aircraftRepository.deleteById(id);
    }
}
