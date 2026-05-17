package udb.edu.sv.mapper;

import org.springframework.stereotype.Component;
import udb.edu.sv.dto.ReservationResponseDTO;
import udb.edu.sv.entity.Flight;
import udb.edu.sv.entity.Passenger;
import udb.edu.sv.entity.Reservation;
import udb.edu.sv.entity.User;

@Component
public class ReservationMapper {

    public ReservationResponseDTO toResponseDTO(Reservation reservation) {
        if (reservation == null) return null;

        Flight flight = reservation.getFlight();
        Passenger passenger = reservation.getPassenger();
        User user = reservation.getUser();

        return ReservationResponseDTO.builder()
                .id(reservation.getId())
                .flightId(flight != null ? flight.getId() : null)
                .flightRouteOrigin(flight != null && flight.getRoute() != null ? flight.getRoute().getOrigin() : null)
                .flightRouteDestination(flight != null && flight.getRoute() != null ? flight.getRoute().getDestination() : null)
                .flightDepartureDate(flight != null ? flight.getDepartureDate() : null)
                .flightDepartureTime(flight != null ? flight.getDepartureTime() : null)
                .flightPrice(flight != null ? flight.getPrice() : null)
                .flightAirlineName(flight != null && flight.getAirline() != null ? flight.getAirline().getName() : null)
                .passengerId(passenger != null ? passenger.getId() : null)
                .passengerFullName(passenger != null ? passenger.getFullName() : null)
                .passengerPassportNumber(passenger != null ? passenger.getPassportNumber() : null)
                .userId(user != null ? user.getId() : null)
                .userEmail(user != null ? user.getEmail() : null)
                .seatNumber(reservation.getSeatNumber())
                .status(reservation.getStatus())
                .reservationDate(reservation.getReservationDate())
                .build();
    }
}
