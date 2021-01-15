package com.recordatoriodemedicamentos.Modelo;

import java.io.Serializable;

public class Paciente implements Serializable {
    String id;
    String nombre;
    String edad;

    public Paciente() {
    }

    public Paciente(String id, String nombre, String edad) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
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

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }
}
