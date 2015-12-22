package com.canchala.julio.practica7;

/**
 * Created by Julio on 22/12/2015.
 */
public class user {
    private String nombre;
    private String correo;
    private String contrase;

    public user(String nombre, String correo, String contrase) {
        this.nombre = nombre;
        this.correo = correo;
        this.contrase = contrase;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getContrase() {
        return contrase;
    }
}
