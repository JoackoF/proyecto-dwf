package udb.edu.sv.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import udb.edu.sv.dto.ApiResponse;
import udb.edu.sv.dto.RouteDTO;
import udb.edu.sv.service.RouteService;
import udb.edu.sv.util.ResponseBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    @PostMapping
    public ResponseEntity<ApiResponse<RouteDTO>> create(@RequestBody RouteDTO routeDTO) {
        RouteDTO savedRoute = routeService.save(routeDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseBuilder.success(savedRoute, "Route created successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<RouteDTO>>> getAll() {
        List<RouteDTO> routes = routeService.findAll();
        return ResponseEntity.ok(
                ResponseBuilder.success(routes, "Route list retrieved successfully")
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RouteDTO>> getById(@PathVariable Long id) {
        return routeService.findById(id)
                .map(dto -> ResponseEntity.ok(ResponseBuilder.success(dto)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ResponseBuilder.error("Route not found with ID: " + id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        routeService.deleteById(id);
        return ResponseEntity.ok(
                ResponseBuilder.success(null, "Route deleted successfully")
        );
    }
}