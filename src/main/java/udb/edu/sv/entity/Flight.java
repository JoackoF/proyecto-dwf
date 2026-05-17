package udb.edu.sv.entity;

import jakarta.persistence.*;
import lombok.*;
import udb.edu.sv.entity.enums.FlightStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "flights")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "airline_id")
    private Airline airline;

    @ManyToOne
    @JoinColumn(name = "aircraft_id")
    private Aircraft aircraft;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;

    private LocalDate departureDate;

    private LocalTime departureTime;

    private BigDecimal price;

    private Integer availableSeats;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private FlightStatus status;
}
