package udb.edu.sv.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import udb.edu.sv.dto.ReservationDTO;
import udb.edu.sv.service.ReservationService;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ReservationDTO create(@RequestBody ReservationDTO reservationDTO) {
        return reservationService.save(reservationDTO);
    }

    @GetMapping
    public List<ReservationDTO> getAll() {
        return reservationService.findAll();
    }

    @GetMapping("/{id}")
    public ReservationDTO getById(@PathVariable Long id) {
        return reservationService.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        reservationService.deleteById(id);
    }
}