package udb.edu.sv.dto;

import lombok.*;
import udb.edu.sv.entity.enums.UserRole;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {

    private Long id;
    private String fullName;
    private String email;
    private UserRole role;
    private LocalDateTime createdAt;
}
