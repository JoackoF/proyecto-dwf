package udb.edu.sv.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import udb.edu.sv.dto.BookingRequestDTO;
import udb.edu.sv.service.BookingService;

@RestController
@RequestMapping("/api/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<String> bookFlight(@RequestBody BookingRequestDTO request) {

        bookingService.bookFlight(request);

        return ResponseEntity.ok("Booking completed successfully");
    }
}