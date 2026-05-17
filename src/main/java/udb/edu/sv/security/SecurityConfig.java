package udb.edu.sv.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import udb.edu.sv.dto.ErrorResponse;

import java.util.List;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(authenticationProvider());
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000", "http://127.0.0.1:3000"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept", "X-Requested-With"));
        config.setExposedHeaders(List.of("Authorization"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .headers(h -> h
                        .frameOptions(f -> f.deny())
                        .contentTypeOptions(c -> {})
                        .referrerPolicy(rp -> rp.policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.NO_REFERRER))
                        .cacheControl(c -> {})
                )
                .exceptionHandling(eh -> eh
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            ErrorResponse body = ErrorResponse.builder()
                                    .status(HttpServletResponse.SC_UNAUTHORIZED)
                                    .message("No autenticado o token inválido")
                                    .build();
                            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            ErrorResponse body = ErrorResponse.builder()
                                    .status(HttpServletResponse.SC_FORBIDDEN)
                                    .message("Acceso denegado: rol insuficiente")
                                    .build();
                            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
                        })
                )
                .authorizeHttpRequests(auth -> auth
                        // ----- Preflight + login + docs públicos -----
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()

                        // ----- /api/users/**: SOLO ADMIN, cualquier método -----
                        .requestMatchers("/api/users/**").hasRole("ADMIN")

                        // ----- /api/airlines /api/aircraft /api/routes -----
                        // GET: cualquier autenticado (necesario para que el cliente arme un vuelo)
                        // Cualquier otro método: solo ADMIN
                        .requestMatchers(HttpMethod.GET, "/api/airlines/**", "/api/aircraft/**", "/api/routes/**")
                                .hasAnyRole("ADMIN", "EMPLOYEE", "CUSTOMER")
                        .requestMatchers(HttpMethod.POST, "/api/airlines/**", "/api/aircraft/**", "/api/routes/**")
                                .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/airlines/**", "/api/aircraft/**", "/api/routes/**")
                                .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/airlines/**", "/api/aircraft/**", "/api/routes/**")
                                .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/airlines/**", "/api/aircraft/**", "/api/routes/**")
                                .hasRole("ADMIN")

                        // ----- /api/flights -----
                        .requestMatchers(HttpMethod.GET, "/api/flights/**").hasAnyRole("ADMIN", "EMPLOYEE", "CUSTOMER")
                        .requestMatchers(HttpMethod.POST, "/api/flights/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/flights/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/flights/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/flights/**").hasRole("ADMIN")

                        // ----- /api/passengers -----
                        // GET (lista/individual): ADMIN/EMPLOYEE (los clientes no necesitan ver pasajeros ajenos)
                        // POST/PUT/DELETE: cubierto por @PreAuthorize en el controller
                        .requestMatchers(HttpMethod.GET, "/api/passengers/**").hasAnyRole("ADMIN", "EMPLOYEE")
                        .requestMatchers("/api/passengers/**").hasAnyRole("ADMIN", "EMPLOYEE", "CUSTOMER")

                        // ----- /api/payments -----
                        // GET: ADMIN/EMPLOYEE; el cliente no necesita listar pagos
                        .requestMatchers(HttpMethod.GET, "/api/payments/**").hasAnyRole("ADMIN", "EMPLOYEE")
                        .requestMatchers("/api/payments/**").hasAnyRole("ADMIN", "EMPLOYEE", "CUSTOMER")

                        // ----- /api/reservations -----
                        // /me: cualquier rol autenticado (filtra por usuario en el service)
                        // Resto: cualquier autenticado; @PreAuthorize en el controller refina
                        .requestMatchers("/api/reservations/**").hasAnyRole("ADMIN", "EMPLOYEE", "CUSTOMER")

                        // ----- /api/booking -----
                        .requestMatchers("/api/booking/**").hasAnyRole("ADMIN", "EMPLOYEE", "CUSTOMER")

                        // ----- /api/claims -----
                        // GET de todos: ADMIN/EMPLOYEE; resto autenticado
                        .requestMatchers(HttpMethod.GET, "/api/claims").hasAnyRole("ADMIN", "EMPLOYEE")
                        .requestMatchers("/api/claims/**").hasAnyRole("ADMIN", "EMPLOYEE", "CUSTOMER")

                        // ----- Cualquier otra petición: autenticado -----
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
