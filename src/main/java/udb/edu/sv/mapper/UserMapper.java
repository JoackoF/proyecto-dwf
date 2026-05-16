package udb.edu.sv.mapper;

import org.springframework.stereotype.Component;
import udb.edu.sv.dto.UserResponseDTO;
import udb.edu.sv.entity.User;

@Component
public class UserMapper {

    public UserResponseDTO toResponseDTO(User user) {
        if (user == null) return null;

        return UserResponseDTO.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
