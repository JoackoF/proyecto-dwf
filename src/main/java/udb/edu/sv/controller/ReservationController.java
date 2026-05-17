package udb.edu.sv.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import udb.edu.sv.dto.ApiResponse;
import udb.edu.sv.dto.ReservationRequestDTO;
import udb.edu.sv.dto.ReservationResponseDTO;
import udb.edu.sv.service.ReservationService;
import udb.edu.sv.util.ResponseBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ApiResponse<ReservationResponseDTO>> create(@Valid @RequestBody ReservationRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseBuilder.success(reservationService.save(dto), "Reserva creada"));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public ResponseEntity<ApiResponse<List<ReservationResponseDTO>>> getAll() {
        return ResponseEntity.ok(
                ResponseBuilder.success(reservationService.findAll(), "Reservas obtenidas")
        );
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<List<ReservationResponseDTO>>> getMine() {
        return ResponseEntity.ok(
                ResponseBuilder.success(reservationService.findByCurrentUser(), "Mis reservas")
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReservationResponseDTO>> getById(@PathVariable Long id) {
        return reservationService.findById(id)
                .map(dto -> ResponseEntity.ok(ResponseBuilder.success(dto, "Reserva obtenida")))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ResponseBuilder.error("Reserva no encontrada con ID: " + id)));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<ReservationResponseDTO>> cancel(@PathVariable Long id) {
        return ResponseEntity.ok(
                ResponseBuilder.success(reservationService.cancel(id), "Reserva cancelada")
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        reservationService.deleteById(id);
        return ResponseEntity.ok(
                ResponseBuilder.success(null, "Reserva eliminada con ID: " + id)
        );
    }
}
