/*Esta prueba es nada mas para airline, pero de manera
genelar se hace lo mismo para los demas servicios xd
 */

package udb.edu.sv;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import udb.edu.sv.entity.Airline;
import udb.edu.sv.repository.AirlineRepository;
import udb.edu.sv.service.impl.AirlineServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AirlineServiceTest {

    @Mock
    private AirlineRepository airlineRepository;

    @InjectMocks
    private AirlineServiceImpl airlineService;

    private Airline airline;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        airline = Airline.builder()
                .id(1L)
                .name("Avianca")
                .code("AV")
                .build();
    }

    @Test
    void testSaveAirline() {

        when(airlineRepository.save(any(Airline.class))).thenReturn(airline);

        Airline saved = airlineService.save(airline);

        assertNotNull(saved);
        assertEquals("Avianca", saved.getName());

        verify(airlineRepository, times(1)).save(airline);
    }

    @Test
    void testFindAllAirlines() {

        when(airlineRepository.findAll()).thenReturn(List.of(airline));

        List<Airline> airlines = airlineService.findAll();

        assertFalse(airlines.isEmpty());
        assertEquals(1, airlines.size());

        verify(airlineRepository, times(1)).findAll();
    }

    @Test
    void testFindAirlineById() {

        when(airlineRepository.findById(1L)).thenReturn(Optional.of(airline));

        Optional<Airline> found = airlineService.findById(1L);

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