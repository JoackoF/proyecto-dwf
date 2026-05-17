package udb.edu.sv.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import udb.edu.sv.dto.ApiResponse;
import udb.edu.sv.dto.PassengerRequestDTO;
import udb.edu.sv.dto.PassengerResponseDTO;
import udb.edu.sv.service.PassengerService;
import udb.edu.sv.util.ResponseBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/passengers")
@RequiredArgsConstructor
public class PassengerController {

    private final PassengerService passengerService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<PassengerResponseDTO>>> getAll() {
        return ResponseEntity.ok(
                ResponseBuilder.success(passengerService.findAll(), "Pasajeros obtenidos")
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PassengerResponseDTO>> getById(@PathVariable Long id) {
        return passengerService.findById(id)
                .map(dto -> ResponseEntity.ok(ResponseBuilder.success(dto, "Pasajero obtenido")))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ResponseBuilder.error("Pasajero no encontrado con ID: " + id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PassengerResponseDTO>> create(@Valid @RequestBody PassengerRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseBuilder.success(passengerService.save(dto), "Pasajero registrado"));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public ResponseEntity<ApiResponse<PassengerResponseDTO>> update(
            @PathVariable Long id,
            @Valid @RequestBody PassengerRequestDTO dto
    ) {
        return ResponseEntity.ok(
                ResponseBuilder.success(passengerService.update(id, dto), "Pasajero actualizado")
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        passengerService.deleteById(id);
        return ResponseEntity.ok(
                ResponseBuilder.success(null, "Pasajero eliminado con ID: " + id)
        );
    }
}
