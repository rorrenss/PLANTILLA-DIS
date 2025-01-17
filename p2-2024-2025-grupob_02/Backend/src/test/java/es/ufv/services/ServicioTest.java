package es.ufv.services;

import es.ufv.inputoutput.InputOutput;
import es.ufv.model.Turismo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ServicioTest {

    @InjectMocks
    private Servicio servicio;

    @Mock
    private InputOutput ioMock;

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
        ArrayList<Turismo> expected = new ArrayList<>();
        when(ioMock.leerTurismo("TurismoComunidades.json")).thenReturn(expected);

        ArrayList<Turismo> result = servicio.getAllTurismo();

        assertNotNull(result);
        assertEquals(expected, result);
        verify(ioMock, times(1)).leerTurismo("TurismoComunidades.json");
    }

    @Test
    public void getTurismoByID() {
        UUID id = UUID.randomUUID();
        Turismo turismo = new Turismo();
        turismo.setId(id);

        ArrayList<Turismo> data = new ArrayList<>();
        data.add(turismo);
        when(ioMock.leerTurismo("TurismoComunidades.json")).thenReturn(data);

        Turismo result = servicio.getTurismoByID(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(ioMock, times(1)).leerTurismo("TurismoComunidades.json");
    }

    @Test
    public void crearTurismo() {
        Turismo newTurismo = new Turismo();
        ArrayList<Turismo> existingData = new ArrayList<>();

        // Ajusta el mock para devolver una lista vacía al leer
        when(ioMock.leerTurismo("TurismoComunidades.json")).thenReturn(existingData);
        // Ajusta el mock para aceptar cualquier lista de tipo ArrayList<Turismo>
        when(ioMock.escribirTurismo(eq("TurismoComunidades.json"), any(ArrayList.class))).thenReturn(existingData);

        ArrayList<Turismo> result = servicio.crearTurismo(newTurismo);

        assertNotNull(result);
        assertTrue(result.contains(newTurismo));
        verify(ioMock, times(1)).leerTurismo("TurismoComunidades.json");
        verify(ioMock, times(1)).escribirTurismo(eq("TurismoComunidades.json"), any(ArrayList.class));
    }

    @Test
    public void updateTurismo() {
        UUID id = UUID.randomUUID();
        Turismo existingTurismo = new Turismo();
        existingTurismo.setId(id);

        Turismo updatedTurismo = new Turismo();
        updatedTurismo.setId(id);

        ArrayList<Turismo> data = new ArrayList<>();
        data.add(existingTurismo);

        // Configurar el mock para devolver una lista específica
        when(ioMock.leerTurismo("TurismoComunidades.json")).thenReturn(data);

        // Asegurar que el mock acepta específicamente una lista del tipo ArrayList<Turismo>
        when(ioMock.escribirTurismo(eq("TurismoComunidades.json"), any(ArrayList.class))).thenReturn(data);

        Turismo result = servicio.updateTurismo(id, updatedTurismo);

        assertNotNull(result);
        assertEquals(updatedTurismo, result);
        verify(ioMock, times(1)).leerTurismo("TurismoComunidades.json");
        verify(ioMock, times(1)).escribirTurismo(eq("TurismoComunidades.json"), any(ArrayList.class));
    }

    @Test
    public void deleteTurismo() {
        UUID id = UUID.randomUUID();
        Turismo existingTurismo = new Turismo();
        existingTurismo.setId(id);

        ArrayList<Turismo> data = new ArrayList<>();
        data.add(existingTurismo);

        // Configurar el mock para devolver la lista simulada
        when(ioMock.leerTurismo("TurismoComunidades.json")).thenReturn(data);

        // Configurar el mock para aceptar específicamente una lista de tipo ArrayList
        when(ioMock.escribirTurismo(eq("TurismoComunidades.json"), any(ArrayList.class))).thenReturn(data);

        Boolean result = servicio.deleteTurismo(id);

        // Verificar que el método devuelve true y se ejecutan los mocks correctamente
        assertTrue(result);
        verify(ioMock, times(1)).leerTurismo("TurismoComunidades.json");
        verify(ioMock, times(1)).escribirTurismo(eq("TurismoComunidades.json"), any(ArrayList.class));
    }

}
