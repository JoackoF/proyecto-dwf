package udb.edu.sv.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import udb.edu.sv.dto.FlightDTO;
import udb.edu.sv.service.FlightService;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;

    @PostMapping
    public FlightDTO create(@RequestBody FlightDTO flightDTO) {
        return flightService.save(flightDTO);
    }

    @GetMapping
    public List<FlightDTO> getAll() {
        return flightService.findAll();
    }

    @GetMapping("/{id}")
    public FlightDTO getById(@PathVariable Long id) {
        return flightService.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        flightService.deleteById(id);
    }
}