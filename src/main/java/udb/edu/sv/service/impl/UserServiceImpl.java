package udb.edu.sv.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import udb.edu.sv.dto.UserRequestDTO;
import udb.edu.sv.dto.UserResponseDTO;
import udb.edu.sv.dto.UserUpdateRequestDTO;
import udb.edu.sv.entity.User;
import udb.edu.sv.entity.enums.UserRole;
import udb.edu.sv.exception.BusinessException;
import udb.edu.sv.exception.DuplicateResourceException;
import udb.edu.sv.exception.ResourceNotFoundException;
import udb.edu.sv.mapper.UserMapper;
import udb.edu.sv.repository.UserRepository;
import udb.edu.sv.security.CurrentUser;
import udb.edu.sv.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final CurrentUser currentUser;

    @Override
    public UserResponseDTO save(UserRequestDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Ya existe un usuario con el email: " + dto.getEmail());
        }

        User user = User.builder()
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(dto.getRole())
                .createdAt(LocalDateTime.now())
                .build();

        return userMapper.toResponseDTO(userRepository.save(user));
    }

    @Override
    public UserResponseDTO update(Long id, UserUpdateRequestDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));

        User actor = currentUser.require();
        boolean isSelfUpdate = actor.getId().equals(id);

        // Prevenir auto-degradación: el admin no puede quitarse el rol ADMIN a sí mismo
        if (isSelfUpdate && user.getRole() == UserRole.ADMIN && dto.getRole() != UserRole.ADMIN) {
            throw new BusinessException(
                    "No puedes quitarte el rol ADMIN a ti mismo. Pídele a otro administrador que lo haga.");
        }

        // Prevenir que el último ADMIN se demote (proteger no quedar sin admins)
        if (user.getRole() == UserRole.ADMIN && dto.getRole() != UserRole.ADMIN
                && countAdmins() <= 1) {
            throw new BusinessException(
                    "No se puede degradar al último administrador del sistema");
        }

        if (!user.getEmail().equalsIgnoreCase(dto.getEmail())
                && userRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Otro usuario ya usa el email: " + dto.getEmail());
        }

        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        return userMapper.toResponseDTO(userRepository.save(user));
    }

    @Override
    public List<UserResponseDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponseDTO)
                .toList();
    }

    @Override
    public Optional<UserResponseDTO> findById(Long id) {
        return userRepository.findById(id).map(userMapper::toResponseDTO);
    }

    @Override
    public Optional<UserResponseDTO> findByEmail(String email) {
        return userRepository.findByEmail(email).map(userMapper::toResponseDTO);
    }

    @Override
    public void deleteById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));

        User actor = currentUser.require();

        // Prevenir auto-eliminación
        if (actor.getId().equals(id)) {
            throw new BusinessException("No puedes eliminarte a ti mismo");
        }

        // Prevenir eliminar al último ADMIN
        if (user.getRole() == UserRole.ADMIN && countAdmins() <= 1) {
            throw new BusinessException("No se puede eliminar al último administrador del sistema");
        }

        userRepository.deleteById(id);
    }

    private long countAdmins() {
        return userRepository.findAll().stream()
                .filter(u -> u.getRole() == UserRole.ADMIN)
                .count();
    }
}
