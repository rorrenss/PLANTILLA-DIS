package es.ufv.model;
import java.util.ArrayList;
import java.util.UUID;
public class Turismo {
    private UUID id;
    private Comunidad comunidadOrigen;
    private Comunidad comunidadDestino;
    private Periodo periodo;
    private int total;

    public Turismo() {
    }
    public Turismo(Comunidad comunidadOrigen, Comunidad comunidadDestino, Periodo periodo, int total) {
        this.id = UUID.randomUUID();
        this.comunidadOrigen = comunidadOrigen;
        this.comunidadDestino = comunidadDestino;
        this.periodo = periodo;
        this.total = total;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Comunidad getComunidadOrigen() {
        return comunidadOrigen;
    }

    public void setComunidadOrigen(Comunidad comunidadOrigen) {
        this.comunidadOrigen = comunidadOrigen;
    }

    public Comunidad getComunidadDestino() {
        return comunidadDestino;
    }

    public void setComunidadDestino(Comunidad comunidadDestino) {
        this.comunidadDestino = comunidadDestino;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", comunidadOrigen=" + comunidadOrigen +
                ", comunidadDestino=" + comunidadDestino +
                ", periodo=" + periodo +
                ", total=" + total +
                '}';
    }


}
