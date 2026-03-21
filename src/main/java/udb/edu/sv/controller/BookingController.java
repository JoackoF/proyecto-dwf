package udb.edu.sv.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponse<Void>> bookFlight(@RequestBody BookingRequestDTO request) {
        bookingService.bookFlight(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseBuilder.success(null, "Booking completed successfully"));
    }
}