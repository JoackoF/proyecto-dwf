package udb.edu.sv.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import udb.edu.sv.dto.PaymentDTO;
import udb.edu.sv.entity.Payment;
import udb.edu.sv.mapper.PaymentMapper;
import udb.edu.sv.repository.PaymentRepository;
import udb.edu.sv.service.PaymentService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Override
    public PaymentDTO save(PaymentDTO paymentDTO) {

        Payment payment = paymentMapper.toEntity(paymentDTO);

        Payment saved = paymentRepository.save(payment);

        return paymentMapper.toDTO(saved);
    }

    @Override
    public List<PaymentDTO> findAll() {

        return paymentRepository.findAll()
                .stream()
                .map(paymentMapper::toDTO)
                .toList();
    }

    @Override
    public Optional<PaymentDTO> findById(Long id) {

        return paymentRepository.findById(id)
                .map(paymentMapper::toDTO);
    }

    @Override
    public void deleteById(Long id) {
        paymentRepository.deleteById(id);
    }
}