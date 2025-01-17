package es.ufv.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UtilsTest {

    private Utils utils;

    @Before
    public void setUp() throws Exception {
        utils = new Utils();
    }

    @After
    public void tearDown() throws Exception {
        utils = null;
    }

    @Test
    public void testDefaultValuesWithDot() {
        String result = utils.defaultValues("123.45");
        assertEquals("0", result);
    }

    @Test
    public void testDefaultValuesWithEmptyString() {
        String result = utils.defaultValues("");
        assertEquals("0", result);
    }

    @Test
    public void testDefaultValuesWithQuotes() {
        String result = utils.defaultValues("\"123\"");
        assertEquals("0", result);
    }

    @Test
    public void testDefaultValuesWithValidString() {
        String result = utils.defaultValues("12345");
        assertEquals("12345", result);
    }

    @Test
    public void testDefaultValuesWithSpecialCharacters() {
        String result = utils.defaultValues("!@#$%");
        assertEquals("!@#$%", result);
    }
}
