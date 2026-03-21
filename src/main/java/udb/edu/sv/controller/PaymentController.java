package udb.edu.sv.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import udb.edu.sv.dto.ApiResponse;
import udb.edu.sv.dto.PaymentDTO;
import udb.edu.sv.service.PaymentService;
import udb.edu.sv.util.ResponseBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<ApiResponse<PaymentDTO>> create(@RequestBody PaymentDTO paymentDTO) {
        PaymentDTO savedPayment = paymentService.save(paymentDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseBuilder.success(savedPayment, "Payment processed successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PaymentDTO>>> getAll() {
        List<PaymentDTO> payments = paymentService.findAll();
        return ResponseEntity.ok(
                ResponseBuilder.success(payments, "Payment records retrieved successfully")
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PaymentDTO>> getById(@PathVariable Long id) {
        return paymentService.findById(id)
                .map(dto -> ResponseEntity.ok(ResponseBuilder.success(dto, "Payment record retrieved successfully by ID:" + id)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ResponseBuilder.error("Payment record not found with ID: " + id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        paymentService.deleteById(id);
        return ResponseEntity.ok(
                ResponseBuilder.success(null, "Payment record deleted successfully by ID: " + id)
        );
    }
}