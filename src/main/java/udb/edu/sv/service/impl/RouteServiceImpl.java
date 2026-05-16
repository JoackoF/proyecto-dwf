package udb.edu.sv.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import udb.edu.sv.dto.RouteRequestDTO;
import udb.edu.sv.dto.RouteResponseDTO;
import udb.edu.sv.entity.Route;
import udb.edu.sv.exception.ResourceNotFoundException;
import udb.edu.sv.mapper.RouteMapper;
import udb.edu.sv.repository.RouteRepository;
import udb.edu.sv.service.RouteService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;
    private final RouteMapper routeMapper;

    @Override
    public RouteResponseDTO save(RouteRequestDTO routeDTO) {
        Route route = routeMapper.toEntity(routeDTO);
        Route saved = routeRepository.save(route);
        return routeMapper.toResponseDTO(saved);
    }

    @Override
    public List<RouteResponseDTO> findAll() {
        return routeRepository.findAll()
                .stream()
                .map(routeMapper::toResponseDTO)
                .toList();
    }

    @Override
    public Optional<RouteResponseDTO> findById(Long id) {
        return routeRepository.findById(id)
                .map(routeMapper::toResponseDTO);
    }

    @Override
    public void deleteById(Long id) {
        if (!routeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Route", id);
        }
        routeRepository.deleteById(id);
    }
}
