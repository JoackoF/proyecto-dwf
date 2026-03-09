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

    @GetMapping
    public List<FlightDTO> getAllFlights() {
        return flightService.findAll();
    }

    @GetMapping("/{id}")
    public FlightDTO getFlightById(@PathVariable Long id) {
        return flightService.findById(id).orElse(null);
    }

    @GetMapping("/search")
    public List<FlightDTO> searchFlights(
            @RequestParam String origin,
            @RequestParam String destination) {
        return flightService.searchFlights(origin, destination);
    }

}