package es.ufv.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.UUID;

public class TurismoTest {

    private Turismo turismo;
    private Comunidad origen;
    private Comunidad destino;
    private Periodo periodo;

    @Before
    public void setUp() {
        turismo = new Turismo();
        origen = new Comunidad("Comunidad Origen", "CO");
        destino = new Comunidad("Comunidad Destino", "CD");
        periodo = new Periodo("2023-01");
    }

    @Test
    public void testGetSetId() {
        UUID id = UUID.randomUUID();
        turismo.setId(id);
        assertEquals(id, turismo.getId());
    }

    @Test
    public void testGetSetComunidadOrigen() {
        turismo.setComunidadOrigen(origen);
        assertEquals(origen, turismo.getComunidadOrigen());
    }

    @Test
    public void testGetSetComunidadDestino() {
        turismo.setComunidadDestino(destino);
        assertEquals(destino, turismo.getComunidadDestino());
    }

    @Test
    public void testGetSetPeriodo() {
        turismo.setPeriodo(periodo);
        assertEquals(periodo, turismo.getPeriodo());
    }

    @Test
    public void testGetSetTotal() {
        int total = 100;
        turismo.setTotal(total);
        assertEquals(total, turismo.getTotal());
    }
}