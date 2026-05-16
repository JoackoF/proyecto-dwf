package udb.edu.sv.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import udb.edu.sv.dto.PaymentRequestDTO;
import udb.edu.sv.dto.PaymentResponseDTO;
import udb.edu.sv.entity.Payment;
import udb.edu.sv.entity.Reservation;
import udb.edu.sv.mapper.PaymentMapper;
import udb.edu.sv.repository.PaymentRepository;
import udb.edu.sv.repository.ReservationRepository;
import udb.edu.sv.service.PaymentService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;
    private final PaymentMapper paymentMapper;

    @Override
    public PaymentResponseDTO save(PaymentRequestDTO dto) {
        Payment payment = paymentMapper.toEntity(dto);

        if (dto.getReservationId() != null) {
            Reservation reservation = reservationRepository.findById(dto.getReservationId())
                    .orElseThrow(() -> new RuntimeException("Reservation not found"));
            payment.setReservation(reservation);
        }

        payment.setPaymentDate(LocalDateTime.now());

        Payment saved = paymentRepository.save(payment);
        return paymentMapper.toResponseDTO(saved);
    }

    @Override
    public List<PaymentResponseDTO> findAll() {
        return paymentRepository.findAll()
                .stream()
                .map(paymentMapper::toResponseDTO)
                .toList();
    }

    @Override
    public Optional<PaymentResponseDTO> findById(Long id) {
        return paymentRepository.findById(id)
                .map(paymentMapper::toResponseDTO);
    }

    @Override
    public void deleteById(Long id) {
        paymentRepository.deleteById(id);
    }
}
