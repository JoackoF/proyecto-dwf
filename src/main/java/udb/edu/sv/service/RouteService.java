package udb.edu.sv.service;

import udb.edu.sv.dto.RouteDTO;

import java.util.List;
import java.util.Optional;

public interface RouteService {

    RouteDTO save(RouteDTO routeDTO);

    List<RouteDTO> findAll();

    Optional<RouteDTO> findById(Long id);

    void deleteById(Long id);
}