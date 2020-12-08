package com.recordatoriodemedicamentos.Modelo;


import java.io.Serializable;

public class Medicamento implements Serializable {
    String id;
    String nombre;
    String unidad;
    String duracion;
    String recordar;
    String priToma;

    public Medicamento() {
    }

    public Medicamento(String id, String nombre, String unidad, String duracion, String recordar, String priToma) {
        this.id = id;
        this.nombre = nombre;
        this.unidad = unidad;
        this.duracion = duracion;
        this.recordar = recordar;
        this.priToma = priToma;
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

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getRecordar() {
        return recordar;
    }

    public void setRecordar(String recordar) {
        this.recordar = recordar;
    }

    public String getPriToma() {
        return priToma;
    }

    public void setPriToma(String priToma) {
        this.priToma = priToma;
    }
}
