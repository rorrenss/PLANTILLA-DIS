package es.ufv.services;
import es.ufv.inputoutput.InputOutput;
import es.ufv.model.Periodo;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.UUID;
import es.ufv.model.Turismo;

@Service
public class Servicio {

    public ArrayList<Turismo> getAllTurismo() {
        InputOutput io = new InputOutput();
        return io.leerTurismo("TurismoComunidades.json");
    }

    public Turismo getTurismoByID(UUID id) {
        InputOutput io = new InputOutput();
        ArrayList<Turismo> emails = io.leerTurismo("TurismoComunidades.json");
        for (Turismo turismo : emails) {
            if (turismo.getId().equals(id)) {
                return turismo;
            }
        }
        return null;
    }
    public ArrayList <Turismo> crearTurismo(Turismo turismo) {
        InputOutput io = new InputOutput();
        ArrayList<Turismo> turismos = io.leerTurismo("TurismoComunidades.json");
        turismos.add(turismo);
        return io.escribirTurismo("TurismoComunidades.json", turismos);
    }

    public Turismo updateTurismo(UUID id, Turismo turismo) {
        InputOutput io = new InputOutput();
        ArrayList<Turismo> turismos = io.leerTurismo("TurismoComunidades.json");
        for (int i = 0; i < turismos.size(); i++) {
            if (turismos.get(i).getId().equals(id)) {
                turismos.set(i, turismo);
                io.escribirTurismo("TurismoComunidades.json",turismos);
                return turismo;
            }
        }
        return null;
    }
    public Boolean deleteTurismo(UUID id) {
        InputOutput io = new InputOutput();
        ArrayList<Turismo> turismo = io.leerTurismo("TurismoComunidades.json");
        for (int i = 0; i < turismo.size(); i++) {
            if (turismo.get(i).getId().equals(id)) {
                turismo.remove(i);
                io.escribirTurismo("TurismoComunidades.json",turismo);
                return true;
            }
        }
        return false;
    }
}
