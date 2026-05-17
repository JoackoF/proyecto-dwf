package udb.edu.sv.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import udb.edu.sv.entity.User;
import udb.edu.sv.entity.enums.UserRole;
import udb.edu.sv.exception.BusinessException;
import udb.edu.sv.repository.UserRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CurrentUser {

    private final UserRepository userRepository;

    public Optional<User> get() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getName() == null) {
            return Optional.empty();
        }
        return userRepository.findByEmail(auth.getName());
    }

    public User require() {
        return get().orElseThrow(() ->
                new BusinessException("No se pudo identificar al usuario autenticado"));
    }

    public boolean hasRole(UserRole role) {
        return get().map(u -> u.getRole() == role).orElse(false);
    }
}
