package udb.edu.sv.dto;

import jakarta.validation.constraints.*;
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
public class FlightRequestDTO {

    @NotNull(message = "El ID de la aerolínea es obligatorio")
    @Min(value = 1, message = "El ID de la aerolínea debe ser positivo")
    private Long airlineId;

    @NotNull(message = "El ID de la aeronave es obligatorio")
    @Min(value = 1, message = "El ID de la aeronave debe ser positivo")
    private Long aircraftId;

    @NotNull(message = "El ID de la ruta es obligatorio")
    @Min(value = 1, message = "El ID de la ruta debe ser positivo")
    private Long routeId;

    @NotNull(message = "La fecha de salida es obligatoria")
    @FutureOrPresent(message = "La fecha de salida debe ser hoy o futura")
    private LocalDate departureDate;

    @NotNull(message = "La hora de salida es obligatoria")
    private LocalTime departureTime;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor que cero")
    @DecimalMax(value = "99999.99", message = "El precio no puede superar 99,999.99")
    @Digits(integer = 5, fraction = 2, message = "El precio admite máximo 5 enteros y 2 decimales")
    private BigDecimal price;

    @Min(value = 0, message = "Los asientos disponibles no pueden ser negativos")
    @Max(value = 1000, message = "Los asientos no pueden superar 1000")
    private Integer availableSeats;

    private FlightStatus status;
}
