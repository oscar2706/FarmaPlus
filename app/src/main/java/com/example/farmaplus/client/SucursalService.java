package com.example.farmaplus.client;

import java.util.ArrayList;
import java.util.List;

class SucursalService {
    public static List<Sucursal> sucursales = new ArrayList<>();

    public static void addSucursal(Sucursal sucursal){
        sucursales.add(sucursal);
    }

    public static void eliminarSucursal(Sucursal sucursal){
        sucursales.remove(sucursal);
    }

    public static void actualizarStatus(Sucursal sucursal){
        sucursales.set(sucursales.indexOf(sucursal), sucursal);
    }

    public static void vaciarLista()
    {
        sucursales.clear();
    }
}
