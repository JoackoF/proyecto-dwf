package udb.edu.sv.dto;

import lombok.*;
import udb.edu.sv.entity.enums.ReservationStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationResponseDTO {

    private Long id;
    private Long flightId;
    private String flightRouteOrigin;
    private String flightRouteDestination;
    private LocalDate flightDepartureDate;
    private LocalTime flightDepartureTime;
    private BigDecimal flightPrice;
    private String flightAirlineName;
    private Long passengerId;
    private String passengerFullName;
    private String passengerPassportNumber;
    private Long userId;
    private String userEmail;
    private String seatNumber;
    private ReservationStatus status;
    private LocalDateTime reservationDate;
}
