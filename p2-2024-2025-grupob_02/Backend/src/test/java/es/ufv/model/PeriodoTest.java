package es.ufv.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.UUID;
public class PeriodoTest {

    @Test
    public void testConstructorWithName() {
        Periodo periodo = new Periodo("2023-01");
        assertEquals("2023-01-01", periodo.getFechaInicio());
        assertEquals("2023-01-31", periodo.getFechaFin());
        assertEquals("2023-01", periodo.getNombre());
    }

    @Test
    public void testConstructorWithDates() {
        Periodo periodo = new Periodo("2023-01-01", "2023-01-31", "Enero 2023");
        assertEquals("2023-01-01", periodo.getFechaInicio());
        assertEquals("2023-01-31", periodo.getFechaFin());
        assertEquals("Enero 2023", periodo.getNombre());
    }

    @Test
    public void testSetFechaInicio() {
        Periodo periodo = new Periodo("2023-01");
        periodo.setFechaInicio("2023-02-01");
        assertEquals("2023-02-01", periodo.getFechaInicio());
    }

    @Test
    public void testSetFechaFin() {
        Periodo periodo = new Periodo("2023-01");
        periodo.setFechaFin("2023-02-28");
        assertEquals("2023-02-28", periodo.getFechaFin());
    }

    @Test
    public void testSetNombre() {
        Periodo periodo = new Periodo("2023-01");
        periodo.setNombre("Febrero 2023");
        assertEquals("Febrero 2023", periodo.getNombre());
    }
}
