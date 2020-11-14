package com.example.farmaplus.client;

import android.os.Bundle;

import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.farmaplus.R;

public class PedidoActual extends Fragment {
    Group group_sinPedido;
    Group group_conPedido;

    public PedidoActual() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pedido_actual, container, false);
        group_sinPedido = view.findViewById(R.id.group_sinPedido);
        group_conPedido = view.findViewById(R.id.group_pedido);

        //TODO: Cambiar la visibilidad dependiendo si hay pedido en curso o no
        group_conPedido.setVisibility(View.GONE);
        //group_sinPedido.setVisibility(View.GONE);

        return view;
    }
}