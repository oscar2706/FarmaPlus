package com.example.farmaplus.pharmacy;

import com.example.farmaplus.Repartidor;
import com.example.farmaplus.client.Direccion;

import java.util.ArrayList;
import java.util.List;

public class RepartidorService {
    public static List<Repartidor> repartidores = new ArrayList<>();

    public static void addRepartidor(Repartidor repartidor){
        repartidores.add(repartidor);
    }

    public static void eliminarRepartidor(Repartidor repartidor){
        repartidores.remove(repartidor);
    }

    public static void actualizarStatus(Repartidor repartidor){
        repartidores.set(repartidores.indexOf(repartidor), repartidor);
    }

    public static void vaciarLista()
    {
        repartidores.clear();
    }
}
