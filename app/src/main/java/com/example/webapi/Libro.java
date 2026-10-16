
package com.example.webapi;

public class Libro {

    private String titulo;
    private String autores;
    private String editorial;

    public Libro(String titulo, String autores,
                 String editorial) {
        this.titulo = titulo;
        this.autores = autores;
        this.editorial = editorial;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutores() {
        return autores;
    }

    public String getEditorial() {
        return editorial;
    }
}
