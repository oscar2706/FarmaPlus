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
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class MedicamentosFragment extends Fragment {
    NavController navController;
    public MedicamentosFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.farmacia_medicamentos, container, false);
        ExtendedFloatingActionButton button_nuevoMedicamento = view.findViewById(R.id.extended_fab_nuevo_medicamento);
        button_nuevoMedicamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_medicamentosFragment_to_nuevoMedicamento);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        Navigation.setViewNavController(view, navController);
    }

}