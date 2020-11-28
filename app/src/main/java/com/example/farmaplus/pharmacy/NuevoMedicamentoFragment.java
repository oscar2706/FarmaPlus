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
import android.widget.Switch;

import com.example.farmaplus.R;
import com.google.android.material.navigation.NavigationView;

public class NuevoMedicamentoFragment extends Fragment implements View.OnClickListener {
    NavController navController;

    public NuevoMedicamentoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.farmacia_nuevo_medicamento, container, false);
        Button button_registrarMedicamento = view.findViewById(R.id.buttonRegistrarMedicamento);
        button_registrarMedicamento.setOnClickListener(this);
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
            case R.id.buttonRegistrarMedicamento:
                navController.navigate(R.id.action_nuevoMedicamento_to_bottomDialogConfirmaMedicamento);
                break;
        }
    }
}