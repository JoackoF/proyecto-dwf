package udb.edu.sv.service;

import udb.edu.sv.dto.PaymentDTO;

import java.util.List;
import java.util.Optional;

public interface PaymentService {

    PaymentDTO save(PaymentDTO paymentDTO);

    List<PaymentDTO> findAll();

    Optional<PaymentDTO> findById(Long id);

    void deleteById(Long id);
}