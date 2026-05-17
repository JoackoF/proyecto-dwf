package udb.edu.sv.dto;

import lombok.*;
import udb.edu.sv.entity.enums.FlightStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightResponseDTO {

    private Long id;
    private Long airlineId;
    private String airlineName;
    private String airlineCode;
    private Long aircraftId;
    private String aircraftModel;
    private Integer aircraftCapacity;
    private Long routeId;
    private String routeOrigin;
    private String routeDestination;
    private Integer routeDurationMinutes;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private BigDecimal price;
    private Integer availableSeats;
    private FlightStatus status;
}
