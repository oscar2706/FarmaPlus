package com.example.farmaplus.client;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import com.example.farmaplus.R;

public class NuevoPedido extends Fragment implements View.OnClickListener {
    Button btn_realizarPedido, btn_subirFoto;
    RadioGroup radioGroup_tipoEnvio;

    public NuevoPedido() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nuevo_pedido, container, false);

        btn_realizarPedido = view.findViewById(R.id.button_realizarPedido);
        btn_subirFoto = view.findViewById(R.id.button_subirReceta);
        btn_realizarPedido.setOnClickListener(this);
        btn_subirFoto.setOnClickListener(this);
        radioGroup_tipoEnvio = view.findViewById(R.id.radioGroup_tipoEnvio);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_realizarPedido:
                int selectedRadioButtonID = radioGroup_tipoEnvio.getCheckedRadioButtonId();
                if(selectedRadioButtonID == R.id.radioButton_sucursal){
                    Navigation.findNavController(getActivity(), R.id.fragment_navigation).navigate(
                            R.id.action_nuevoPedido_to_dialogSleccionaSucursal);
                } else {
                    Navigation.findNavController(getActivity(), R.id.fragment_navigation).navigate(
                            R.id.action_nuevoPedido_to_dialogSeleccionaDireccion);
                }
                break;
            case R.id.button_subirReceta:
                Navigation.findNavController(getActivity(), R.id.fragment_navigation).navigate(
                        R.id.action_nuevoPedido_to_dialogSubirFoto);
                break;
        }
    }
}