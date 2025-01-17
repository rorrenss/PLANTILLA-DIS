package es.ufv.controllers;

import es.ufv.model.Turismo;
import es.ufv.services.Servicio;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MainControllerTest {

    @InjectMocks
    private MainController mainController;

    @Mock
    private Servicio servicio;

    private AutoCloseable closeable;

    @Before
    public void setUp() throws Exception {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @After
    public void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    public void getAllTurismo() {
        // Mock de respuesta del servicio
        ArrayList<Turismo> expectedTurismoList = new ArrayList<>();
        when(servicio.getAllTurismo()).thenReturn(expectedTurismoList);

        // Llamada al método del controlador
        ArrayList<Turismo> result = mainController.getAllTurismo();

        // Validaciones
        assertNotNull(result);
        assertEquals(expectedTurismoList, result);
        verify(servicio, times(1)).getAllTurismo();
    }

    @Test
    public void getTurismolByTo() {
        UUID id = UUID.randomUUID();
        Turismo turismo = new Turismo();
        turismo.setId(id);

        // Mock de respuesta del servicio
        when(servicio.getTurismoByID(id)).thenReturn(turismo);

        // Llamada al método del controlador
        ResponseEntity<Turismo> response = mainController.getTurismolByTo(id);

        // Validaciones
        assertNotNull(response);
        assertEquals(turismo, response.getBody());
        verify(servicio, times(1)).getTurismoByID(id);
    }

    @Test
    public void createTurismo() {
        Turismo turismo = new Turismo();
        ArrayList<Turismo> turismoList = new ArrayList<>();

        // Mock de respuesta del servicio
        when(servicio.crearTurismo(turismo)).thenReturn(turismoList);

        // Llamada al método del controlador
        ResponseEntity<ArrayList<Turismo>> response = mainController.createTurismo(turismo);

        // Validaciones
        assertNotNull(response);
        assertEquals(turismoList, response.getBody());
        verify(servicio, times(1)).crearTurismo(turismo);
    }

    @Test
    public void updateTurismo() {
        UUID id = UUID.randomUUID();
        Turismo turismo = new Turismo();
        turismo.setId(id);

        // Mock de respuesta del servicio
        when(servicio.updateTurismo(id, turismo)).thenReturn(turismo);

        // Llamada al método del controlador
        ResponseEntity<Turismo> response = mainController.updateTurismo(id, turismo);

        // Validaciones
        assertNotNull(response);
        assertEquals(turismo, response.getBody());
        verify(servicio, times(1)).updateTurismo(id, turismo);
    }

    @Test
    public void deleteTurismo() {
        UUID id = UUID.randomUUID();

        // Mock de respuesta del servicio
        when(servicio.deleteTurismo(id)).thenReturn(true);

        // Llamada al método del controlador
        ResponseEntity<Boolean> response = mainController.deleteTurismo(id);

        // Validaciones
        assertNotNull(response);
        assertTrue(response.getBody());
        verify(servicio, times(1)).deleteTurismo(id);
    }
}
