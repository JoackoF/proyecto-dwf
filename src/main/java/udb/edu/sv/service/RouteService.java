package udb.edu.sv.service;

import udb.edu.sv.dto.RouteRequestDTO;
import udb.edu.sv.dto.RouteResponseDTO;

import java.util.List;
import java.util.Optional;

public interface RouteService {

    RouteResponseDTO save(RouteRequestDTO routeDTO);

    RouteResponseDTO update(Long id, RouteRequestDTO routeDTO);

    List<RouteResponseDTO> findAll();

    Optional<RouteResponseDTO> findById(Long id);

    void deleteById(Long id);
}
