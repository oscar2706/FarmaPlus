package com.example.farmaplus.client;

import java.util.ArrayList;
import java.util.List;

public class DireccionService {
    public static List<Direccion> direcciones = new ArrayList<>();

    public static void addDireccion(Direccion direccion){
        direcciones.add(direccion);
    }

    public static void eliminarPedido(Direccion direccion){
        direcciones.remove(direccion);
    }

    public static void actualizarStatus(Direccion direccion){
        direcciones.set(direcciones.indexOf(direccion), direccion);
    }

    public static void vaciarLista()
    {
        direcciones.clear();
    }
}
