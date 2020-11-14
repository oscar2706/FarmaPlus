package com.example.farmaplus.client;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.farmaplus.R;

public class MisDirecciones extends Fragment {

    public MisDirecciones() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mis_direcciones, container, false);
        Button buttonAddDirection = view.findViewById(R.id.button_agregarDireccion);
        buttonAddDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomDialog bottomDialog_addAddress = new BottomDialog();
                bottomDialog_addAddress.show(getFragmentManager(), "");
            }
        });
        return view;
    }
}