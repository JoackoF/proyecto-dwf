package udb.edu.sv.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import udb.edu.sv.dto.FlightDTO;
import udb.edu.sv.mapper.FlightMapper;
import udb.edu.sv.repository.FlightRepository;
import udb.edu.sv.service.FlightService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final FlightMapper flightMapper;

    @Override
    public FlightDTO save(FlightDTO flightDTO) {
        return flightMapper.toDTO(
                flightRepository.save(
                        flightMapper.toEntity(flightDTO)));
    }

    @Override
    public List<FlightDTO> findAll() {
        return flightRepository.findAll()
                .stream()
                .map(flightMapper::toDTO)
                .collect(Collectors.toList());
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

    @Override
    public List<FlightDTO> searchFlights(String origin, String destination) {

        return flightRepository
                .findByRouteOriginAndRouteDestination(origin, destination)
                .stream()
                .map(flightMapper::toDTO)
                .collect(Collectors.toList());

    }
}