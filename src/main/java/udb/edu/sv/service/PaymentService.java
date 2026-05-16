package udb.edu.sv.service;

import udb.edu.sv.dto.PaymentRequestDTO;
import udb.edu.sv.dto.PaymentResponseDTO;

import java.util.List;
import java.util.Optional;

public interface PaymentService {

    PaymentResponseDTO save(PaymentRequestDTO paymentDTO);

    List<PaymentResponseDTO> findAll();

    Optional<PaymentResponseDTO> findById(Long id);

    void deleteById(Long id);
}
