package com.recordatoriodemedicamentos.Modelo;


import java.io.Serializable;

public class Medicamento implements Serializable {
    String id;
    String nombre;
    String unidad;
    String FechaInicio;
    String FechaFinal;
    String Recordar;
    String PrimeraToma;
    String UltimaToma;
    String Existencia;

    public Medicamento() {
    }

    public Medicamento(String id, String nombre, String unidad, String fechaInicio, String fechaFinal, String recordar, String primeraToma, String ultimaToma, String existencia) {
        this.id = id;
        this.nombre = nombre;
        this.unidad = unidad;
        FechaInicio = fechaInicio;
        FechaFinal = fechaFinal;
        Recordar = recordar;
        PrimeraToma = primeraToma;
        UltimaToma = ultimaToma;
        Existencia = existencia;
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

    public String getFechaInicio() {
        return FechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        FechaInicio = fechaInicio;
    }

    public String getFechaFinal() {
        return FechaFinal;
    }

    public void setFechaFinal(String fechaFinal) {
        FechaFinal = fechaFinal;
    }

    public String getRecordar() {
        return Recordar;
    }

    public void setRecordar(String recordar) {
        Recordar = recordar;
    }

    public String getPrimeraToma() {
        return PrimeraToma;
    }

    public void setPrimeraToma(String primeraToma) {
        PrimeraToma = primeraToma;
    }

    public String getUltimaToma() {
        return UltimaToma;
    }

    public void setUltimaToma(String ultimaToma) {
        UltimaToma = ultimaToma;
    }

    public String getExistencia() {
        return Existencia;
    }

    public void setExistencia(String existencia) {
        Existencia = existencia;
    }
}
