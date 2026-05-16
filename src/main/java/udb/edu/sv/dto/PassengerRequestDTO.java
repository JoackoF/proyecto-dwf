package udb.edu.sv.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PassengerRequestDTO {

    private String fullName;
    private LocalDate birthDate;
    private String passportNumber;
}
