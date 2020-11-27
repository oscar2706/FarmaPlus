package com.example.farmaplus.pharmacy;

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

public class NuevoPedidoDetalleFragment extends Fragment implements View.OnClickListener {
    NavController navController;

    public NuevoPedidoDetalleFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.farmacia_nuevo_pedido_detalle, container, false);
        Button button_aceptar = view.findViewById(R.id.buttonAceptar);
        button_aceptar.setOnClickListener(this);
        Button button_cancelar = view.findViewById(R.id.buttonCancelar);
        button_cancelar.setOnClickListener(this);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        Navigation.setViewNavController(view, navController);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonAceptar:
                navController.navigate(R.id.action_nuevoPedidoDetalleFragment_to_bottomDialogSeleccionaRepartidor);
                break;
            case R.id.buttonCancelar:
                break;
        }
    }
}