package udb.edu.sv.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDTO {

    private Long id;

    private Long flightId;

    private String passengerName;

    private String seatNumber;

    private String status;
}