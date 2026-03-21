package udb.edu.sv.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import udb.edu.sv.dto.ApiResponse;
import udb.edu.sv.dto.ReservationDTO;
import udb.edu.sv.service.ReservationService;
import udb.edu.sv.util.ResponseBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ApiResponse<ReservationDTO>> create(@RequestBody ReservationDTO reservationDTO) {
        ReservationDTO savedReservation = reservationService.save(reservationDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseBuilder.success(savedReservation, "Reservation created successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ReservationDTO>>> getAll() {
        List<ReservationDTO> list = reservationService.findAll();
        return ResponseEntity.ok(
                ResponseBuilder.success(list, "Reservation list retrieved successfully")
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReservationDTO>> getById(@PathVariable Long id) {
        return reservationService.findById(id)
                .map(dto -> ResponseEntity.ok(ResponseBuilder.success(dto, "Reservation retrieved successfully by ID: " + id)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ResponseBuilder.error("Reservation not found with ID: " + id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        reservationService.deleteById(id);
        return ResponseEntity.ok(
                ResponseBuilder.success(null, "Reservation deleted successfully by ID: " + id)
        );
    }
}