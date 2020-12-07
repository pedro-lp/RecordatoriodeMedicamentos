package com.recordatoriodemedicamentos.Modelo;

public class Medicamento {
    String id;
    String nombre;
    String dosis;

    public Medicamento(String id, String nombre, String dosis) {
        this.id = id;
        this.nombre = nombre;
        this.dosis = dosis;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }
}
