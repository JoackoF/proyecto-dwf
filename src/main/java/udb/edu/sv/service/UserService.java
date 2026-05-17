package udb.edu.sv.service;

import udb.edu.sv.dto.UserRequestDTO;
import udb.edu.sv.dto.UserResponseDTO;
import udb.edu.sv.dto.UserUpdateRequestDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserResponseDTO save(UserRequestDTO userDTO);

    UserResponseDTO update(Long id, UserUpdateRequestDTO userDTO);

    List<UserResponseDTO> findAll();

    Optional<UserResponseDTO> findById(Long id);

    Optional<UserResponseDTO> findByEmail(String email);

    void deleteById(Long id);
}
