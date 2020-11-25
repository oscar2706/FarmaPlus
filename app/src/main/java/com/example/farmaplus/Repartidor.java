package com.example.farmaplus;

import androidx.annotation.Nullable;

import com.example.farmaplus.client.Pedido;

public class Repartidor {
    private String IdRepartidor;
    private String nombre;
    private String apellidos;
    private String usuario;
    private String password;
    private String fechaRegistro;
    private String fotoRepartidor;

    public Repartidor() {
    }

    public Repartidor(String idRepartidor, String nombre, String usuario, String password, String fechaRegistro, String fotoRepartidor, String apellido) {
        IdRepartidor = idRepartidor;
        this.nombre = nombre;
        this.usuario = usuario;
        this.password = password;
        this.fechaRegistro = fechaRegistro;
        this.fotoRepartidor = fotoRepartidor;
        this.apellidos = apellido;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getIdRepartidor() {
        return IdRepartidor;
    }

    public void setIdRepartidor(String idRepartidor) {
        IdRepartidor = idRepartidor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getFotoRepartidor() {
        return fotoRepartidor;
    }

    public void setFotoRepartidor(String fotoRepartidor) {
        this.fotoRepartidor = fotoRepartidor;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return IdRepartidor.equals(((Repartidor)obj).IdRepartidor);
    }
}
