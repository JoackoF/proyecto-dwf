package udb.edu.sv.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import udb.edu.sv.dto.AirlineRequestDTO;
import udb.edu.sv.dto.AirlineResponseDTO;
import udb.edu.sv.dto.ApiResponse;
import udb.edu.sv.service.AirlineService;
import udb.edu.sv.util.ResponseBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/airlines")
@RequiredArgsConstructor
public class AirlineController {

    private final AirlineService airlineService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<AirlineResponseDTO>>> getAll() {
        return ResponseEntity.ok(
                ResponseBuilder.success(airlineService.findAll(), "Aerolíneas obtenidas")
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AirlineResponseDTO>> getById(@PathVariable Long id) {
        return airlineService.findById(id)
                .map(dto -> ResponseEntity.ok(ResponseBuilder.success(dto, "Aerolínea obtenida")))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ResponseBuilder.error("Aerolínea no encontrada con ID: " + id)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<AirlineResponseDTO>> create(@Valid @RequestBody AirlineRequestDTO dto) {
        AirlineResponseDTO saved = airlineService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseBuilder.success(saved, "Aerolínea creada"));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<AirlineResponseDTO>> update(
            @PathVariable Long id,
            @Valid @RequestBody AirlineRequestDTO dto
    ) {
        return ResponseEntity.ok(
                ResponseBuilder.success(airlineService.update(id, dto), "Aerolínea actualizada")
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        airlineService.deleteById(id);
        return ResponseEntity.ok(
                ResponseBuilder.success(null, "Aerolínea eliminada con ID: " + id)
        );
    }
}
