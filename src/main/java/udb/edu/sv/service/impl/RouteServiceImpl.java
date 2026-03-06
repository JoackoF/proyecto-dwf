package udb.edu.sv.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import udb.edu.sv.dto.RouteDTO;
import udb.edu.sv.entity.Route;
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
    public RouteDTO save(RouteDTO routeDTO) {

        Route route = routeMapper.toEntity(routeDTO);
        Route saved = routeRepository.save(route);

        return routeMapper.toDTO(saved);
    }

    @Override
    public List<RouteDTO> findAll() {
        return routeRepository.findAll()
                .stream()
                .map(routeMapper::toDTO)
                .toList();
    }

    @Override
    public Optional<RouteDTO> findById(Long id) {
        return routeRepository.findById(id)
                .map(routeMapper::toDTO);
    }

    @Override
    public void deleteById(Long id) {
        routeRepository.deleteById(id);
    }
}