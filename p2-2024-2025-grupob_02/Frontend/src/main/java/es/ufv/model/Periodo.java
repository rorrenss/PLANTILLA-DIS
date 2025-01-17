package es.ufv.model;

public class Periodo {
    String fechaInicio;
    String fechaFin;
    String nombre;

    public Periodo(String nombre){
        this.nombre = nombre;
        String anio=nombre.substring(0,4);
        String mes=nombre.substring(5);
        this.fechaInicio = anio+"-"+mes+"-01";
        this.fechaFin = anio+"-"+mes+"-31";
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Override
    public String toString() {
        return "Periodo{" +
                "fechaInicio='" + fechaInicio + '\'' +
                ", fechaFin='" + fechaFin + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
