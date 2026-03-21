package udb.edu.sv.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import udb.edu.sv.dto.AircraftDTO;
import udb.edu.sv.dto.ApiResponse;
import udb.edu.sv.service.AircraftService;
import udb.edu.sv.util.ResponseBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/aircraft")
@RequiredArgsConstructor
public class AircraftController {

    private final AircraftService aircraftService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<AircraftDTO>>> getAll() {
        List<AircraftDTO> list = aircraftService.findAll();
        return ResponseEntity.ok(
                ResponseBuilder.success(list, "Aircraft list retrieved successfully")
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AircraftDTO>> getById(@PathVariable Long id) {
        return aircraftService.findById(id)
                .map(dto -> ResponseEntity.ok(ResponseBuilder.success(dto)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ResponseBuilder.error("Aircraft not found with ID: " + id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AircraftDTO>> create(@RequestBody AircraftDTO dto) {
        AircraftDTO savedDto = aircraftService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseBuilder.success(savedDto, "Aircraft created successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {

        if (aircraftService.findById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseBuilder.error("Aircraft not found with ID: " + id));
        }

        aircraftService.deleteById(id);

        return ResponseEntity.ok(
                ResponseBuilder.success(null, "Aircraft deleted successfully")
        );
    }
}