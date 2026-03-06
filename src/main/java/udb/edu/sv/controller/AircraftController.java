package udb.edu.sv.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import udb.edu.sv.dto.AircraftDTO;
import udb.edu.sv.entity.Aircraft;
import udb.edu.sv.mapper.AircraftMapper;
import udb.edu.sv.service.AircraftService;

import java.util.List;

@RestController
@RequestMapping("/api/aircraft")
@RequiredArgsConstructor
public class AircraftController {

    private final AircraftService aircraftService;

    @GetMapping
    public List<AircraftDTO> getAll() {
        return aircraftService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AircraftDTO> getById(@PathVariable Long id) {
        return aircraftService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public AircraftDTO create(@RequestBody AircraftDTO dto) {
        return aircraftService.save(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        aircraftService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}