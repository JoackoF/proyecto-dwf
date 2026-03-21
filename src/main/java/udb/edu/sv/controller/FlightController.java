package udb.edu.sv.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import udb.edu.sv.dto.ApiResponse;
import udb.edu.sv.dto.FlightDTO;
import udb.edu.sv.service.FlightService;
import udb.edu.sv.util.ResponseBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<FlightDTO>>> getAllFlights() {
        List<FlightDTO> flights = flightService.findAll();
        return ResponseEntity.ok(
                ResponseBuilder.success(flights, "Flight list retrieved successfully")
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<FlightDTO>> getFlightById(@PathVariable Long id) {
        return flightService.findById(id)
                .map(dto -> ResponseEntity.ok(ResponseBuilder.success(dto, "Flight retrieved successfully by ID: " + id)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ResponseBuilder.error("Flight not found with ID: " + id)));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<FlightDTO>>> searchFlights(
            @RequestParam String origin,
            @RequestParam String destination
    ) {
        List<FlightDTO> foundFlights = flightService.searchFlights(origin, destination);
        String message = foundFlights.isEmpty() ? "No flights found for the specified route" : "Available flights retrieved successfully";

        return ResponseEntity.ok(ResponseBuilder.success(foundFlights, message));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<FlightDTO>> createFlight(@RequestBody FlightDTO dto) {
        FlightDTO savedFlight = flightService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseBuilder.success(savedFlight, "Flight created successfully"));
    }
}