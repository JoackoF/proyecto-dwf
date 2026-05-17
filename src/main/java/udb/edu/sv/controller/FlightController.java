package udb.edu.sv.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import udb.edu.sv.dto.ApiResponse;
import udb.edu.sv.dto.FlightRequestDTO;
import udb.edu.sv.dto.FlightResponseDTO;
import udb.edu.sv.entity.enums.FlightStatus;
import udb.edu.sv.service.FlightService;
import udb.edu.sv.util.ResponseBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<FlightResponseDTO>>> getAllFlights() {
        return ResponseEntity.ok(
                ResponseBuilder.success(flightService.findAll(), "Vuelos obtenidos")
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<FlightResponseDTO>> getFlightById(@PathVariable Long id) {
        return flightService.findById(id)
                .map(dto -> ResponseEntity.ok(ResponseBuilder.success(dto, "Vuelo obtenido")))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ResponseBuilder.error("Vuelo no encontrado con ID: " + id)));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<FlightResponseDTO>>> searchFlights(
            @RequestParam String origin,
            @RequestParam String destination
    ) {
        List<FlightResponseDTO> found = flightService.searchFlights(origin, destination);
        String msg = found.isEmpty() ? "No se encontraron vuelos para la ruta indicada" : "Vuelos obtenidos";
        return ResponseEntity.ok(ResponseBuilder.success(found, msg));
    }

    @GetMapping("/{id}/taken-seats")
    public ResponseEntity<ApiResponse<List<String>>> getTakenSeats(@PathVariable Long id) {
        return ResponseEntity.ok(
                ResponseBuilder.success(flightService.findTakenSeats(id), "Asientos ocupados del vuelo")
        );
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<FlightResponseDTO>> createFlight(@Valid @RequestBody FlightRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseBuilder.success(flightService.save(dto), "Vuelo creado"));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<FlightResponseDTO>> updateFlight(
            @PathVariable Long id,
            @Valid @RequestBody FlightRequestDTO dto
    ) {
        return ResponseEntity.ok(
                ResponseBuilder.success(flightService.update(id, dto), "Vuelo actualizado")
        );
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<FlightResponseDTO>> changeStatus(
            @PathVariable Long id,
            @Valid @RequestBody FlightStatusUpdateDTO body
    ) {
        return ResponseEntity.ok(
                ResponseBuilder.success(flightService.changeStatus(id, body.getStatus()),
                        "Estado del vuelo actualizado")
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        flightService.deleteById(id);
        return ResponseEntity.ok(
                ResponseBuilder.success(null, "Vuelo eliminado con ID: " + id)
        );
    }

    @Data
    public static class FlightStatusUpdateDTO {
        @NotNull(message = "El estado es obligatorio (SCHEDULED, BOARDING, DEPARTED, ARRIVED, DELAYED, CANCELLED)")
        private FlightStatus status;
    }
}
