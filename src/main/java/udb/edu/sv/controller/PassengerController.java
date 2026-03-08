package udb.edu.sv.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import udb.edu.sv.dto.PassengerDTO;
import udb.edu.sv.service.PassengerService;

import java.util.List;

@RestController
@RequestMapping("/api/passengers")
@RequiredArgsConstructor
public class PassengerController {

    private final PassengerService passengerService;

    @PostMapping
    public PassengerDTO create(@RequestBody PassengerDTO passengerDTO) {
        return passengerService.save(passengerDTO);
    }

    @GetMapping
    public List<PassengerDTO> getAll() {
        return passengerService.findAll();
    }

    @GetMapping("/{id}")
    public PassengerDTO getById(@PathVariable Long id) {
        return passengerService.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        passengerService.deleteById(id);
    }
}