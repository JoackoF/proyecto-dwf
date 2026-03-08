package udb.edu.sv.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import udb.edu.sv.dto.AirlineDTO;
import udb.edu.sv.service.AirlineService;

import java.util.List;

@RestController
@RequestMapping("/api/airlines")
@RequiredArgsConstructor
public class AirlineController {

    private final AirlineService airlineService;

    @PostMapping
    public AirlineDTO create(@RequestBody AirlineDTO airlineDTO) {
        return airlineService.save(airlineDTO);
    }

    @GetMapping
    public List<AirlineDTO> getAll() {
        return airlineService.findAll();
    }

    @GetMapping("/{id}")
    public AirlineDTO getById(@PathVariable Long id) {
        return airlineService.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        airlineService.deleteById(id);
    }
}