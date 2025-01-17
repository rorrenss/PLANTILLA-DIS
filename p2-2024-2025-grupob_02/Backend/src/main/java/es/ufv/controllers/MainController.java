package es.ufv.controllers;
import es.ufv.model.Turismo;
import es.ufv.services.Servicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequestMapping("/api/p2")
public class MainController {

    private final Servicio servicio;

    @Autowired
    public MainController(Servicio servicio) {
        this.servicio = servicio;
    }

    @GetMapping("/turismo")
    public ArrayList<Turismo> getAllTurismo() {
        return servicio.getAllTurismo();
    }

    @GetMapping("/turismo/{id}")
    public ResponseEntity <Turismo> getTurismolByTo(@PathVariable UUID id) {
        return ResponseEntity.ok(servicio.getTurismoByID(id));
    }
    @PostMapping("/turismo")
    public ResponseEntity <ArrayList<Turismo>> createTurismo(@RequestBody Turismo turismo) {
        return ResponseEntity.ok(servicio.crearTurismo(turismo));
    }
    @PutMapping("/turismo/{id}")
    public ResponseEntity <Turismo> updateTurismo(@PathVariable UUID id, @RequestBody Turismo turismo) {
        return ResponseEntity.ok(servicio.updateTurismo(id, turismo));
    }
    @DeleteMapping("/turismo/{id}")
    public ResponseEntity <Boolean> deleteTurismo(@PathVariable UUID id) {
        return ResponseEntity.ok(servicio.deleteTurismo(id));
    }
}