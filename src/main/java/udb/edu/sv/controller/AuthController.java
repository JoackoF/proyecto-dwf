package udb.edu.sv.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import udb.edu.sv.dto.ApiResponse;
import udb.edu.sv.dto.AuthResponseDTO;
import udb.edu.sv.dto.LoginRequestDTO;
import udb.edu.sv.dto.RegisterRequestDTO;
import udb.edu.sv.entity.User;
import udb.edu.sv.entity.enums.UserRole;
import udb.edu.sv.exception.BusinessException;
import udb.edu.sv.exception.DuplicateResourceException;
import udb.edu.sv.repository.UserRepository;
import udb.edu.sv.security.JwtUtil;
import udb.edu.sv.util.ResponseBuilder;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> login(@Valid @RequestBody LoginRequestDTO request) {

        log.info("[auth] login attempt for email={}", request.getEmail());

        boolean userExists = userRepository.findByEmail(request.getEmail()).isPresent();
        log.info("[auth] user record found in DB? {}", userExists);

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (BadCredentialsException ex) {
            log.warn("[auth] BadCredentialsException for email={} -> {}", request.getEmail(), ex.getMessage());
            throw new BusinessException("Credenciales inválidas");
        } catch (AuthenticationException ex) {
            log.warn("[auth] {} for email={} -> {}", ex.getClass().getSimpleName(), request.getEmail(), ex.getMessage());
            throw new BusinessException("Credenciales inválidas");
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BusinessException("Credenciales inválidas"));

        log.info("[auth] login success for userId={} role={}", user.getId(), user.getRole());

        AuthResponseDTO body = buildAuthResponse(user);
        return ResponseEntity.ok(ResponseBuilder.success(body, "Autenticación exitosa"));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> register(@Valid @RequestBody RegisterRequestDTO request) {

        log.info("[auth] register attempt for email={}", request.getEmail());

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new DuplicateResourceException("El email ya está registrado");
        }

        User user = userRepository.save(User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.CUSTOMER)
                .createdAt(LocalDateTime.now())
                .build());

        log.info("[auth] register success for userId={}", user.getId());

        AuthResponseDTO body = buildAuthResponse(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseBuilder.success(body, "Registro exitoso"));
    }

    private AuthResponseDTO buildAuthResponse(User user) {
        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().name(),
                user.getId(),
                user.getFullName()
        );
        return AuthResponseDTO.builder()
                .token(token)
                .tokenType("Bearer")
                .expiresInMs(jwtUtil.getExpirationMs())
                .userId(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .role(user.getRole())
                .build();
    }
}
