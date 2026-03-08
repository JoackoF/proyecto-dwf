package udb.edu.sv.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import udb.edu.sv.dto.AirlineDTO;
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
    public AirlineDTO save(AirlineDTO airlineDTO) {

        Airline airline = airlineMapper.toEntity(airlineDTO);
        Airline saved = airlineRepository.save(airline);

        return airlineMapper.toDTO(saved);
    }

    @Override
    public List<AirlineDTO> findAll() {

        return airlineRepository.findAll()
                .stream()
                .map(airlineMapper::toDTO)
                .toList();
    }

    @Override
    public Optional<AirlineDTO> findById(Long id) {

        return airlineRepository.findById(id)
                .map(airlineMapper::toDTO);
    }

    @Override
    public void deleteById(Long id) {

        airlineRepository.deleteById(id);
    }
}