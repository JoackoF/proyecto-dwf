package udb.edu.sv.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import udb.edu.sv.dto.AircraftDTO;
import udb.edu.sv.entity.Aircraft;
import udb.edu.sv.mapper.AircraftMapper;
import udb.edu.sv.repository.AircraftRepository;
import udb.edu.sv.service.AircraftService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AircraftServiceImpl implements AircraftService {

    private final AircraftRepository aircraftRepository;
    private final AircraftMapper aircraftMapper;

    @Override
    public AircraftDTO save(AircraftDTO aircraftDTO) {

        Aircraft aircraft = aircraftMapper.toEntity(aircraftDTO);
        Aircraft saved = aircraftRepository.save(aircraft);

        return aircraftMapper.toDTO(saved);
    }

    @Override
    public List<AircraftDTO> findAll() {
        return aircraftRepository.findAll()
                .stream()
                .map(aircraftMapper::toDTO)
                .toList();
    }

    @Override
    public Optional<AircraftDTO> findById(Long id) {
        return aircraftRepository.findById(id)
                .map(aircraftMapper::toDTO);
    }

    @Override
    public void deleteById(Long id) {
        aircraftRepository.deleteById(id);
    }
}