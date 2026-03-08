package udb.edu.sv.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import udb.edu.sv.dto.PaymentDTO;
import udb.edu.sv.service.PaymentService;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public PaymentDTO create(@RequestBody PaymentDTO paymentDTO) {
        return paymentService.save(paymentDTO);
    }

    @GetMapping
    public List<PaymentDTO> getAll() {
        return paymentService.findAll();
    }

    @GetMapping("/{id}")
    public PaymentDTO getById(@PathVariable Long id) {
        return paymentService.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        paymentService.deleteById(id);
    }
}