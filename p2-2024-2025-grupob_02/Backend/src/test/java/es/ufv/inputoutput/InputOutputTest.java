package es.ufv.inputoutput;

import com.google.gson.Gson;
import es.ufv.model.Turismo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.*;

public class InputOutputTest {

    private InputOutput inputOutput;
    private final String testFilePath = "test_turismo.json";

    @Before
    public void setUp() throws Exception {
        inputOutput = new InputOutput();
    }

    @After
    public void tearDown() throws Exception {
        // Elimina el archivo de prueba después de cada test
        File file = new File(testFilePath);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void leerTurismo() throws Exception {
        // Crea un archivo de prueba con datos de turismo
        ArrayList<Turismo> expectedList = new ArrayList<>();
        Turismo turismo = new Turismo();
        turismo.setId(UUID.randomUUID());
        turismo.setTotal(100);
        expectedList.add(turismo);

        Gson gson = new Gson();
        gson.toJson(expectedList, new FileWriter(testFilePath));

        // Prueba leerTurismo
        ArrayList<Turismo> result = inputOutput.leerTurismo(testFilePath);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expectedList.get(0).getId(), result.get(0).getId());
        assertEquals(expectedList.get(0).getTotal(), result.get(0).getTotal());
    }

    @Test
    public void escribirTurismo() {
        // Crea una lista de turismo para escribir
        ArrayList<Turismo> turismoList = new ArrayList<>();
        Turismo turismo = new Turismo();
        turismo.setId(UUID.randomUUID());
        turismo.setTotal(200);
        turismoList.add(turismo);

        // Prueba escribirTurismo
        ArrayList<Turismo> result = inputOutput.escribirTurismo(testFilePath, turismoList);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(turismoList.get(0).getId(), result.get(0).getId());
        assertEquals(turismoList.get(0).getTotal(), result.get(0).getTotal());

        // Verifica que los datos se escribieron correctamente
        ArrayList<Turismo> writtenData = inputOutput.leerTurismo(testFilePath);
        assertNotNull(writtenData);
        assertEquals(1, writtenData.size());
        assertEquals(turismoList.get(0).getId(), writtenData.get(0).getId());
        assertEquals(turismoList.get(0).getTotal(), writtenData.get(0).getTotal());
    }
}
