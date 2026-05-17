package udb.edu.sv.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import udb.edu.sv.dto.ApiResponse;
import udb.edu.sv.dto.RouteRequestDTO;
import udb.edu.sv.dto.RouteResponseDTO;
import udb.edu.sv.service.RouteService;
import udb.edu.sv.util.ResponseBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<RouteResponseDTO>>> getAll() {
        return ResponseEntity.ok(
                ResponseBuilder.success(routeService.findAll(), "Rutas obtenidas")
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RouteResponseDTO>> getById(@PathVariable Long id) {
        return routeService.findById(id)
                .map(dto -> ResponseEntity.ok(ResponseBuilder.success(dto, "Ruta obtenida")))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ResponseBuilder.error("Ruta no encontrada con ID: " + id)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<RouteResponseDTO>> create(@Valid @RequestBody RouteRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseBuilder.success(routeService.save(dto), "Ruta creada"));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<RouteResponseDTO>> update(
            @PathVariable Long id,
            @Valid @RequestBody RouteRequestDTO dto
    ) {
        return ResponseEntity.ok(
                ResponseBuilder.success(routeService.update(id, dto), "Ruta actualizada")
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        routeService.deleteById(id);
        return ResponseEntity.ok(
                ResponseBuilder.success(null, "Ruta eliminada con ID: " + id)
        );
    }
}
