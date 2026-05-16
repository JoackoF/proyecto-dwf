package udb.edu.sv.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import udb.edu.sv.dto.AirlineRequestDTO;
import udb.edu.sv.dto.AirlineResponseDTO;
import udb.edu.sv.entity.Airline;
import udb.edu.sv.mapper.AirlineMapper;
import udb.edu.sv.repository.AirlineRepository;
import udb.edu.sv.service.AirlineService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AirlineServiceImpl implements AirlineService {

    private final AirlineRepository airlineRepository;
    private final AirlineMapper airlineMapper;

    @Override
    public AirlineResponseDTO save(AirlineRequestDTO airlineDTO) {
        Airline airline = airlineMapper.toEntity(airlineDTO);
        Airline saved = airlineRepository.save(airline);
        return airlineMapper.toResponseDTO(saved);
    }

    @Override
    public List<AirlineResponseDTO> findAll() {
        return airlineRepository.findAll()
                .stream()
                .map(airlineMapper::toResponseDTO)
                .toList();
    }

    @Override
    public Optional<AirlineResponseDTO> findById(Long id) {
        return airlineRepository.findById(id)
                .map(airlineMapper::toResponseDTO);
    }

    @Override
    public void deleteById(Long id) {
        airlineRepository.deleteById(id);
    }
}
