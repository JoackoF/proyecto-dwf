package udb.edu.sv.dto;

import lombok.Data;
import udb.edu.sv.entity.enums.PaymentType;

@Data
public class BookingRequestDTO {

    private Long flightId;
    private String seatNumber;
    private PaymentType paymentType;
    private PassengerDTO passenger;

}