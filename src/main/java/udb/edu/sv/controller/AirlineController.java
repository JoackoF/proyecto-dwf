package udb.edu.sv.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import udb.edu.sv.dto.AirlineDTO;
import udb.edu.sv.dto.ApiResponse;
import udb.edu.sv.service.AirlineService;
import udb.edu.sv.util.ResponseBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/airlines")
@RequiredArgsConstructor
public class AirlineController {

    private final AirlineService airlineService;

    @PostMapping
    public ResponseEntity<ApiResponse<AirlineDTO>> create(@RequestBody AirlineDTO airlineDTO) {
        AirlineDTO savedDto = airlineService.save(airlineDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseBuilder.success(savedDto, "Airline created successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AirlineDTO>>> getAll() {
        List<AirlineDTO> list = airlineService.findAll();
        return ResponseEntity.ok(
                ResponseBuilder.success(list, "Airline list retrieved successfully")
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AirlineDTO>> getById(@PathVariable Long id) {
        return airlineService.findById(id)
                .map(dto -> ResponseEntity.ok(ResponseBuilder.success(dto)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ResponseBuilder.error("Airline not found with ID: " + id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        airlineService.deleteById(id);
        return ResponseEntity.ok(
                ResponseBuilder.success(null, "Airline deleted successfully")
        );
    }
}