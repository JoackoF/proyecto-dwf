package udb.edu.sv.dto;

import lombok.*;
import udb.edu.sv.entity.enums.UserRole;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponseDTO {

    private String token;
    private String tokenType;
    private Long expiresInMs;
    private Long userId;
    private String email;
    private String fullName;
    private UserRole role;
}
