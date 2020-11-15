package com.example.farmaplus.client;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.example.farmaplus.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomDialogSleccionaSucursal extends BottomSheetDialogFragment implements View.OnClickListener {
    Button buttonAceptar, buttonCancelar;

    public BottomDialogSleccionaSucursal() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_dialog_pedido_sucursal, container, false);
        buttonAceptar = view.findViewById(R.id.button_aceptar);
        buttonCancelar = view.findViewById(R.id.button_cancelar);
        buttonAceptar.setOnClickListener(this);
        buttonCancelar.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_cancelar:
                Navigation.findNavController(getActivity(), R.id.fragment_navigation).navigate(
                        R.id.action_dialogSleccionaSucursal_to_nuevoPedido);
                break;
            case R.id.button_aceptar:
                Navigation.findNavController(getActivity(), R.id.fragment_navigation).navigate(
                        R.id.action_bottomDialogSleccionaSucursal_to_principalCliente_fragment2);
                break;
        }
    }
}
