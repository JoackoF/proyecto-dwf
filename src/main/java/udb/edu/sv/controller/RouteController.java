package udb.edu.sv.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import udb.edu.sv.dto.RouteDTO;
import udb.edu.sv.service.RouteService;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    @PostMapping
    public RouteDTO create(@RequestBody RouteDTO routeDTO) {
        return routeService.save(routeDTO);
    }

    @GetMapping
    public List<RouteDTO> getAll() {
        return routeService.findAll();
    }

    @GetMapping("/{id}")
    public RouteDTO getById(@PathVariable Long id) {
        return routeService.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        routeService.deleteById(id);
    }
}