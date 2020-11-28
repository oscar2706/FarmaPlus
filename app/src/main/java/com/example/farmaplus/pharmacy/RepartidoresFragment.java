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

import com.example.farmaplus.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class RepartidoresFragment extends Fragment implements View.OnClickListener {
    NavController navController;

    public RepartidoresFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.farmacia_repartidores, container, false);
        ExtendedFloatingActionButton button_nuevoRepartidor = view.findViewById(R.id.extended_fab_nuevo_repartidor);
        button_nuevoRepartidor.setOnClickListener(this);
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
        navController.navigate(R.id.action_repartidores_to_nuevoRepartidor);
    }
}