package udb.edu.sv.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import udb.edu.sv.dto.AirlineRequestDTO;
import udb.edu.sv.dto.AirlineResponseDTO;
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
    public ResponseEntity<ApiResponse<AirlineResponseDTO>> create(@RequestBody AirlineRequestDTO airlineDTO) {
        AirlineResponseDTO savedDto = airlineService.save(airlineDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseBuilder.success(savedDto, "Airline created successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AirlineResponseDTO>>> getAll() {
        List<AirlineResponseDTO> list = airlineService.findAll();
        return ResponseEntity.ok(
                ResponseBuilder.success(list, "Airline list retrieved successfully")
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AirlineResponseDTO>> getById(@PathVariable Long id) {
        return airlineService.findById(id)
                .map(dto -> ResponseEntity.ok(ResponseBuilder.success(dto, "Airline retrieved successfully by ID: " + id)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ResponseBuilder.error("Airline not found with ID: " + id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        airlineService.deleteById(id);
        return ResponseEntity.ok(
                ResponseBuilder.success(null, "Airline deleted successfully by ID: " + id)
        );
    }
}
