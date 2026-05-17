package udb.edu.sv.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import udb.edu.sv.dto.ApiResponse;
import udb.edu.sv.dto.BookingRequestDTO;
import udb.edu.sv.service.BookingService;
import udb.edu.sv.util.ResponseBuilder;

@RestController
@RequestMapping("/api/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','CUSTOMER')")
    public ResponseEntity<ApiResponse<Void>> bookFlight(@Valid @RequestBody BookingRequestDTO request) {
        bookingService.bookFlight(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseBuilder.success(null, "Reserva completada"));
    }
}
