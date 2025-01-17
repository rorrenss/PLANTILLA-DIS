package es.ufv.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import es.ufv.model.Comunidad;
import es.ufv.model.Periodo;
import es.ufv.model.Turismo;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.io.File;
import java.io.IOException;
@Service
public class TurismoService implements Serializable {

    private final String BASE_URL = "http://backendJV:8082/api/p2/turismo";
    private final HttpClient client = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    public List<Turismo> getAllTurismo() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return gson.fromJson(response.body(), new TypeToken<List<Turismo>>() {}.getType());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Turismo getTurismoById(UUID id) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL + "/" + id))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return gson.fromJson(response.body(), Turismo.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void createTurismo(Turismo turismo) {
        try {
            String json = gson.toJson(turismo);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL))
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .header("Content-Type", "application/json")
                    .build();
            client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateTurismo(UUID id, Turismo turismo) {
        try {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL + "/" + id))
                    .PUT(HttpRequest.BodyPublishers.ofString(gson.toJson(turismo)))
                    .header("Content-Type", "application/json")
                    .build();
            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteTurismo(UUID id) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL + "/" + id))
                    .DELETE()
                    .build();
            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public HashMap<String, List<Turismo>> getComunidadesAgrupadas() {
        HashMap<String, List<Turismo>> comunidadesAgrupadas = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        File file = new File("Comunidades_Agrupadas.json"); // Ajusta la ruta del archivo

        try {
            // Leer y mapear los datos desde el JSON
            HashMap<String, List<HashMap<String, Object>>> rawData = objectMapper.readValue(
                    file, new TypeReference<HashMap<String, List<HashMap<String, Object>>>>() {}
            );

            // Procesar cada entrada del JSON para convertirla en el modelo Turismo
            for (String key : rawData.keySet()) {
                List<HashMap<String, Object>> entries = rawData.get(key);
                List<Turismo> turismos = entries.stream().map(entry -> {
                    // Crear objetos Comunidad y Periodo a partir de los datos
                    Comunidad comunidadOrigen = new Comunidad(
                            ((HashMap<String, String>) entry.get("from")).get("comunidad"),
                            ((HashMap<String, String>) entry.get("from")).get("provincia")
                    );

                    Comunidad comunidadDestino = new Comunidad(
                            ((HashMap<String, String>) entry.get("to")).get("comunidad"),
                            ((HashMap<String, String>) entry.get("to")).get("provincia")
                    );

                    Periodo periodo = new Periodo(
                            ((HashMap<String, String>) entry.get("timeRange")).get("period")
                    );

                    // Crear objeto Turismo con los datos deserializados
                    return new Turismo(
                            comunidadOrigen,
                            comunidadDestino,
                            periodo,
                            (int) entry.get("total")
                    );
                }).toList();

                comunidadesAgrupadas.put(key, turismos);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al leer el archivo Comunidades_Agrupadas.json", e);
        }

        return comunidadesAgrupadas;
    }
}
