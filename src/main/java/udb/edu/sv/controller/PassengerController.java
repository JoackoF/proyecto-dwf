package udb.edu.sv.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import udb.edu.sv.dto.ApiResponse;
import udb.edu.sv.dto.PassengerDTO;
import udb.edu.sv.service.PassengerService;
import udb.edu.sv.util.ResponseBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/passengers")
@RequiredArgsConstructor
public class PassengerController {

    private final PassengerService passengerService;

    @PostMapping
    public ResponseEntity<ApiResponse<PassengerDTO>> create(@RequestBody PassengerDTO passengerDTO) {
        PassengerDTO savedPassenger = passengerService.save(passengerDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseBuilder.success(savedPassenger, "Passenger registered successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PassengerDTO>>> getAll() {
        List<PassengerDTO> passengers = passengerService.findAll();
        return ResponseEntity.ok(
                ResponseBuilder.success(passengers, "Passenger list retrieved successfully")
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PassengerDTO>> getById(@PathVariable Long id) {
        return passengerService.findById(id)
                .map(dto -> ResponseEntity.ok(ResponseBuilder.success(dto, "Passenger retrieved successfully by ID" + id)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ResponseBuilder.error("Passenger not found with ID: " + id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        passengerService.deleteById(id);
        return ResponseEntity.ok(
                ResponseBuilder.success(null, "Passenger deleted successfully by ID: " + id)
        );
    }
}