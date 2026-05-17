package udb.edu.sv.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import udb.edu.sv.dto.AircraftRequestDTO;
import udb.edu.sv.dto.AircraftResponseDTO;
import udb.edu.sv.dto.ApiResponse;
import udb.edu.sv.service.AircraftService;
import udb.edu.sv.util.ResponseBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/aircraft")
@RequiredArgsConstructor
public class AircraftController {

    private final AircraftService aircraftService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<AircraftResponseDTO>>> getAll() {
        return ResponseEntity.ok(
                ResponseBuilder.success(aircraftService.findAll(), "Aeronaves obtenidas")
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AircraftResponseDTO>> getById(@PathVariable Long id) {
        return aircraftService.findById(id)
                .map(dto -> ResponseEntity.ok(ResponseBuilder.success(dto, "Aeronave obtenida")))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ResponseBuilder.error("Aeronave no encontrada con ID: " + id)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<AircraftResponseDTO>> create(@Valid @RequestBody AircraftRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseBuilder.success(aircraftService.save(dto), "Aeronave creada"));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<AircraftResponseDTO>> update(
            @PathVariable Long id,
            @Valid @RequestBody AircraftRequestDTO dto
    ) {
        return ResponseEntity.ok(
                ResponseBuilder.success(aircraftService.update(id, dto), "Aeronave actualizada")
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        aircraftService.deleteById(id);
        return ResponseEntity.ok(
                ResponseBuilder.success(null, "Aeronave eliminada con ID: " + id)
        );
    }
}
