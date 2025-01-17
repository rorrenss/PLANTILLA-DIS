package es.ufv.model;
import java.util.ArrayList;
import java.util.UUID;
public class Turismo {
    private UUID id;
    private Comunidad comunidadOrigen;
    private Comunidad comunidadDestino;
    private Periodo periodo;
    private int total;

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

    public Turismo(Comunidad comunidadOrigen, Comunidad comunidadDestino, Periodo periodo, String total) {
        this.id = UUID.randomUUID();
        this.comunidadOrigen = comunidadOrigen;
        this.comunidadDestino = comunidadDestino;
        this.periodo = periodo;
        String[] QuitarComas = total.split(",");
        StringBuilder sb = new StringBuilder();
        for(String dato:QuitarComas){
            sb.append(dato);
        }
        this.total= Integer.parseInt(sb.toString());
    }

    public Turismo() {
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
