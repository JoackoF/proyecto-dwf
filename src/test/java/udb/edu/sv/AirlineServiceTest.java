package udb.edu.sv;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import udb.edu.sv.dto.AirlineRequestDTO;
import udb.edu.sv.dto.AirlineResponseDTO;
import udb.edu.sv.entity.Airline;
import udb.edu.sv.mapper.AirlineMapper;
import udb.edu.sv.repository.AirlineRepository;
import udb.edu.sv.service.impl.AirlineServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AirlineServiceTest {

    @Mock
    private AirlineRepository airlineRepository;

    @Mock
    private AirlineMapper airlineMapper;

    @InjectMocks
    private AirlineServiceImpl airlineService;

    private Airline airline;
    private AirlineRequestDTO airlineRequest;
    private AirlineResponseDTO airlineResponse;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        airline = Airline.builder()
                .id(1L)
                .name("Avianca")
                .code("AV")
                .build();

        airlineRequest = AirlineRequestDTO.builder()
                .name("Avianca")
                .code("AV")
                .build();

        airlineResponse = AirlineResponseDTO.builder()
                .id(1L)
                .name("Avianca")
                .code("AV")
                .build();
    }

    @Test
    void testSaveAirline() {

        when(airlineMapper.toEntity(any(AirlineRequestDTO.class))).thenReturn(airline);
        when(airlineRepository.save(any(Airline.class))).thenReturn(airline);
        when(airlineMapper.toResponseDTO(any(Airline.class))).thenReturn(airlineResponse);

        AirlineResponseDTO saved = airlineService.save(airlineRequest);

        assertNotNull(saved);
        assertEquals("Avianca", saved.getName());

        verify(airlineRepository, times(1)).save(any(Airline.class));
    }

    @Test
    void testFindAllAirlines() {

        when(airlineRepository.findAll()).thenReturn(List.of(airline));
        when(airlineMapper.toResponseDTO(any(Airline.class))).thenReturn(airlineResponse);

        List<AirlineResponseDTO> airlines = airlineService.findAll();

        assertFalse(airlines.isEmpty());
        assertEquals(1, airlines.size());

        verify(airlineRepository, times(1)).findAll();
    }

    @Test
    void testFindAirlineById() {

        when(airlineRepository.findById(1L)).thenReturn(Optional.of(airline));
        when(airlineMapper.toResponseDTO(any(Airline.class))).thenReturn(airlineResponse);

        Optional<AirlineResponseDTO> found = airlineService.findById(1L);

        assertTrue(found.isPresent());
        assertEquals("Avianca", found.get().getName());

        verify(airlineRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteAirline() {

        doNothing().when(airlineRepository).deleteById(1L);

        airlineService.deleteById(1L);

        verify(airlineRepository, times(1)).deleteById(1L);
    }
}
