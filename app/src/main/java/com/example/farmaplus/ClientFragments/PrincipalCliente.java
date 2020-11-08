package com.example.farmaplus.ClientFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.farmaplus.R;

public class PrincipalCliente extends Fragment implements View.OnClickListener {
    Button button_NuevoPedido;

    public PrincipalCliente() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_principal_cliente, container, false);
        button_NuevoPedido = view.findViewById(R.id.button_NuevoPedido);
        button_NuevoPedido.setOnClickListener(this);
        view.findViewById(R.id.cardView_Historial).setOnClickListener(this);
        view.findViewById(R.id.cardView_Medicamentos).setOnClickListener(this);
        view.findViewById(R.id.cardView_PedidoActual).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {

        NavController navController = Navigation.findNavController(view);
        Navigation.setViewNavController(view, navController);

        switch (view.getId()) {
            case R.id.button_NuevoPedido:
                navController.navigate(R.id.action_principalCliente_to_nuevoPedido);
                break;
            case R.id.cardView_PedidoActual:
                navController.navigate(R.id.action_principalCliente_to_pedidoActual);
                break;
            case R.id.cardView_Historial:
                navController.navigate(R.id.action_principalCliente_to_historialPedidos);
                break;
            case R.id.cardView_Medicamentos:
                navController.navigate(R.id.action_principalCliente_to_medicamentos);
                break;
        }
    }
}