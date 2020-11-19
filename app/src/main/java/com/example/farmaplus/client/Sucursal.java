package com.example.farmaplus.client;

import androidx.annotation.Nullable;

public class Sucursal {
    private String idSuc;
    private String latitud;
    private String longitud;
    private String direccionSuc;
    private String nombreSuc;
    private String estado;

    public Sucursal() {
    }

    public Sucursal(String idSuc, String latitud, String longitud, String direccionSuc, String nombreSuc, String estado) {
        this.idSuc = idSuc;
        this.latitud = latitud;
        this.longitud = longitud;
        this.direccionSuc = direccionSuc;
        this.nombreSuc = nombreSuc;
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getIdSuc() {
        return idSuc;
    }

    public void setIdSuc(String idSuc) {
        this.idSuc = idSuc;
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

    public String getDireccionSuc() {
        return direccionSuc;
    }

    public void setDireccionSuc(String direccionSuc) {
        this.direccionSuc = direccionSuc;
    }

    public String getNombreSuc() {
        return nombreSuc;
    }

    public void setNombreSuc(String nombreSuc) {
        this.nombreSuc = nombreSuc;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return idSuc.equals(((Sucursal)obj).idSuc);
    }
}
