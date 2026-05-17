package udb.edu.sv.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import udb.edu.sv.dto.ApiResponse;
import udb.edu.sv.dto.ClaimRequestDTO;
import udb.edu.sv.dto.ClaimResponseDTO;
import udb.edu.sv.service.ClaimService;
import udb.edu.sv.util.ResponseBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/claims")
@RequiredArgsConstructor
public class ClaimController {

    private final ClaimService claimService;

    @PostMapping
    public ResponseEntity<ApiResponse<ClaimResponseDTO>> create(@Valid @RequestBody ClaimRequestDTO claimDTO) {
        ClaimResponseDTO saved = claimService.save(claimDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseBuilder.success(saved, "Reclamo registrado"));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public ResponseEntity<ApiResponse<List<ClaimResponseDTO>>> getAll() {
        return ResponseEntity.ok(
                ResponseBuilder.success(claimService.findAll(), "Reclamos obtenidos")
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public ResponseEntity<ApiResponse<ClaimResponseDTO>> getById(@PathVariable Long id) {
        return claimService.findById(id)
                .map(dto -> ResponseEntity.ok(ResponseBuilder.success(dto, "Reclamo obtenido")))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ResponseBuilder.error("Reclamo no encontrado con ID: " + id)));
    }

    @GetMapping("/by-reservation/{reservationId}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public ResponseEntity<ApiResponse<List<ClaimResponseDTO>>> getByReservation(@PathVariable Long reservationId) {
        return ResponseEntity.ok(
                ResponseBuilder.success(
                        claimService.findByReservationId(reservationId),
                        "Reclamos de la reserva obtenidos"
                )
        );
    }

    @PatchMapping("/{id}/resolve")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public ResponseEntity<ApiResponse<ClaimResponseDTO>> resolve(@PathVariable Long id) {
        return ResponseEntity.ok(
                ResponseBuilder.success(claimService.resolve(id), "Reclamo resuelto")
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        claimService.deleteById(id);
        return ResponseEntity.ok(
                ResponseBuilder.success(null, "Reclamo eliminado con ID: " + id)
        );
    }
}
