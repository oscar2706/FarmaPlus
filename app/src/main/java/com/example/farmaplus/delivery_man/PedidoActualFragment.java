package com.example.farmaplus.delivery_man;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.farmaplus.R;

public class PedidoActualFragment extends Fragment implements View.OnClickListener {
    NavController navController;

    public PedidoActualFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        Navigation.setViewNavController(view, navController);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.repartidor_pedido_actual, container, false);

        Button button_entregaPedido = view.findViewById(R.id.button_entregaPedido);
        button_entregaPedido.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_entregaPedido:
                navController.navigate(R.id.action_pedidoActualRepartidor_to_bottomDialogConfirmaEntrega);
                break;
        }
    }
}