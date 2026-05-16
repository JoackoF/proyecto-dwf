package udb.edu.sv.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationRequestDTO {

    private Long flightId;
    private Long passengerId;
    private Long userId;
    private String seatNumber;
}
