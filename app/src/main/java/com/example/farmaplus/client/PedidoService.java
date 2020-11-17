package com.example.farmaplus.client;

import java.util.ArrayList;
import java.util.List;

public class PedidoService {
    public static List<Pedido> pedidos = new ArrayList<>();

    public static void addPedido(Pedido pedido){
        pedidos.add(pedido);
    }

    public static void eliminarPedido(Pedido pedido){
        pedidos.remove(pedido);
    }

    public static void actualizarStatus(Pedido pedido){
        pedidos.set(pedidos.indexOf(pedido), pedido);
    }

    public static void vaciarLista()
    {
        pedidos.clear();
    }
}
