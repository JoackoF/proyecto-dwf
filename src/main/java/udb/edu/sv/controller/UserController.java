package udb.edu.sv.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import udb.edu.sv.dto.ApiResponse;
import udb.edu.sv.dto.UserRequestDTO;
import udb.edu.sv.dto.UserResponseDTO;
import udb.edu.sv.service.UserService;
import udb.edu.sv.util.ResponseBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponseDTO>> create(@Valid @RequestBody UserRequestDTO userDTO) {
        UserResponseDTO saved = userService.save(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseBuilder.success(saved, "Usuario creado exitosamente"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> getAll() {
        return ResponseEntity.ok(
                ResponseBuilder.success(userService.findAll(), "Lista de usuarios obtenida")
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getById(@PathVariable Long id) {
        return userService.findById(id)
                .map(dto -> ResponseEntity.ok(ResponseBuilder.success(dto, "Usuario obtenido por ID: " + id)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ResponseBuilder.error("Usuario no encontrado con ID: " + id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok(
                ResponseBuilder.success(null, "Usuario eliminado con ID: " + id)
        );
    }
}
