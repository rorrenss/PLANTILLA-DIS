package es.ufv.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ComunidadTest {

    private Comunidad comunidad;

    @Before
    public void setUp() {
        comunidad = new Comunidad("Andalucía", "Sevilla");
    }

    @Test
    public void testGetSetComunidad() {
        comunidad.setComunidad("Cataluña");
        assertEquals("Cataluña", comunidad.getComunidad());
    }

    @Test
    public void testGetSetProvincia() {
        comunidad.setProvincia("Barcelona");
        assertEquals("Barcelona", comunidad.getProvincia());
    }

    @Test
    public void testToString() {
        String expected = "Comunidad{comunidad='Andalucía', provincia='Sevilla'}";
        assertEquals(expected, comunidad.toString());
    }
}
