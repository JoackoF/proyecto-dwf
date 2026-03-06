package udb.edu.sv.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightDTO {

    private Long id;

    private Long airlineId;

    private Long aircraftId;

    private Long routeId;

    private LocalDate departureDate;

    private LocalTime departureTime;

    private BigDecimal price;

    private Integer availableSeats;

}