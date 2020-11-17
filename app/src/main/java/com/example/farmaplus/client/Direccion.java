package com.example.farmaplus.client;

import androidx.annotation.Nullable;

public class Direccion {
    private String idDom;
    private String nombreDireccion;
    private String latitud;
    private String longitud;
    private String direccion;
    private String idUser;

    public Direccion(){

    }

    public Direccion(String nombreDireccion, String latitud, String longitud, String direccion, String idUser, String idDom) {
        this.nombreDireccion = nombreDireccion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.direccion = direccion;
        this.idUser = idUser;
        this.idDom = idDom;
    }

    public String getIdDom() {
        return idDom;
    }

    public void setIdDom(String idDom) {
        this.idDom = idDom;
    }

    public String getNombreDireccion() {
        return nombreDireccion;
    }

    public void setNombreDireccion(String nombreDireccion) {
        this.nombreDireccion = nombreDireccion;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return idDom.equals(((Direccion)obj).idDom);
    }
}
