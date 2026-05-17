package udb.edu.sv.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import udb.edu.sv.dto.ApiResponse;
import udb.edu.sv.dto.PaymentRequestDTO;
import udb.edu.sv.dto.PaymentResponseDTO;
import udb.edu.sv.service.PaymentService;
import udb.edu.sv.util.ResponseBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<ApiResponse<PaymentResponseDTO>> create(@Valid @RequestBody PaymentRequestDTO paymentDTO) {
        PaymentResponseDTO savedPayment = paymentService.save(paymentDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseBuilder.success(savedPayment, "Pago registrado"));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public ResponseEntity<ApiResponse<List<PaymentResponseDTO>>> getAll() {
        return ResponseEntity.ok(
                ResponseBuilder.success(paymentService.findAll(), "Pagos obtenidos")
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public ResponseEntity<ApiResponse<PaymentResponseDTO>> getById(@PathVariable Long id) {
        return paymentService.findById(id)
                .map(dto -> ResponseEntity.ok(ResponseBuilder.success(dto, "Pago obtenido")))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ResponseBuilder.error("Pago no encontrado con ID: " + id)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        paymentService.deleteById(id);
        return ResponseEntity.ok(
                ResponseBuilder.success(null, "Pago eliminado con ID: " + id)
        );
    }
}
