package es.ufv.model;

public class Comunidad {
    private String comunidad;
    private String provincia;

    public String getComunidad() {
        return comunidad;
    }

    public void setComunidad(String comunidad) {
        this.comunidad = comunidad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public Comunidad(String comunidad, String provincia) {
        this.comunidad = comunidad;
        this.provincia = provincia;
    }

    @Override
    public String toString() {
        return "Comunidad{" +
                "comunidad='" + comunidad + '\'' +
                ", provincia='" + provincia + '\'' +
                '}';
    }
}
