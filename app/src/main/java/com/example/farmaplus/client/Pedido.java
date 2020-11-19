package com.example.farmaplus.client;

import androidx.annotation.Nullable;

public class Pedido {
    private String id;
    private String comentarios;
    private String tipoEntrega;
    private String estadoPedido;
    private String url;
    private String user;
    private String enCurso;
    private String repartidor;
    private String fechaPedido;
    private String lugarEntrega;

    public Pedido(){

    }

    public Pedido(String id, String comentarios, String tipoEntrega, String estadoPedido, String url, String encurso, String repartidor, String fecha, String lugarEntrega) {
        this.id = id;
        this.comentarios = comentarios;
        this.tipoEntrega = tipoEntrega;
        this.estadoPedido = estadoPedido;
        this.url = url;
        this.enCurso = encurso;
        this.repartidor = repartidor;
        this.fechaPedido = fecha;
        this.lugarEntrega = lugarEntrega;
    }

    public String getLugarEntrega() {
        return lugarEntrega;
    }

    public void setLugarEntrega(String lugarEntrega) {
        this.lugarEntrega = lugarEntrega;
    }

    public String getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(String fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public String getRepartidor() {
        return repartidor;
    }

    public void setRepartidor(String repartidor) {
        this.repartidor = repartidor;
    }

    public String getEnCurso() {
        return enCurso;
    }

    public void setEnCurso(String enCurso) {
        this.enCurso = enCurso;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getTipoEntrega() {
        return tipoEntrega;
    }

    public void setTipoEntrega(String tipoEntrega) {
        this.tipoEntrega = tipoEntrega;
    }

    public String getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(String estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return id.equals(((Pedido)obj).id);
    }
}
