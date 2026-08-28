package com.example.webapi;

public class Personaje {

    private String nombre;
    private String estado;
    private String especie;

    public Personaje(String nombre, String estado, String especie) {
        this.nombre = nombre;
        this.estado = estado;
        this.especie = especie;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEstado() {
        return estado;
    }

    public String getEspecie() {
        return especie;
    }
}