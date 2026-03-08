package udb.edu.sv.dto;

import lombok.*;
import udb.edu.sv.entity.enums.ReservationStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDTO {

    private Long id;

    private Long flightId;

    private Long passengerId;

    private Long userId;

    private String seatNumber;

    private ReservationStatus status;

    private LocalDateTime reservationDate;
}