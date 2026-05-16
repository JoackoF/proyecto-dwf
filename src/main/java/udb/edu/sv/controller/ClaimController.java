package udb.edu.sv.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
                .body(ResponseBuilder.success(saved, "Claim filed successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ClaimResponseDTO>>> getAll() {
        return ResponseEntity.ok(
                ResponseBuilder.success(claimService.findAll(), "Claims retrieved successfully")
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ClaimResponseDTO>> getById(@PathVariable Long id) {
        return claimService.findById(id)
                .map(dto -> ResponseEntity.ok(ResponseBuilder.success(dto, "Claim retrieved successfully by ID: " + id)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ResponseBuilder.error("Claim not found with ID: " + id)));
    }

    @GetMapping("/by-reservation/{reservationId}")
    public ResponseEntity<ApiResponse<List<ClaimResponseDTO>>> getByReservation(@PathVariable Long reservationId) {
        return ResponseEntity.ok(
                ResponseBuilder.success(claimService.findByReservationId(reservationId), "Claims for reservation retrieved")
        );
    }

    @PatchMapping("/{id}/resolve")
    public ResponseEntity<ApiResponse<ClaimResponseDTO>> resolve(@PathVariable Long id) {
        return ResponseEntity.ok(
                ResponseBuilder.success(claimService.resolve(id), "Claim resolved successfully")
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        claimService.deleteById(id);
        return ResponseEntity.ok(
                ResponseBuilder.success(null, "Claim deleted successfully by ID: " + id)
        );
    }
}
