package udb.edu.sv.service;

import udb.edu.sv.entity.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentService {

    Payment save(Payment payment);

    List<Payment> findAll();

    Optional<Payment> findById(Long id);

    void deleteById(Long id);
}