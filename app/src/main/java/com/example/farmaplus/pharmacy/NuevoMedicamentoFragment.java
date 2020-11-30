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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.example.farmaplus.R;
import com.google.android.material.navigation.NavigationView;

public class NuevoMedicamentoFragment extends Fragment implements View.OnClickListener {
    NavController navController;
    AutoCompleteTextView editTextFilledExposedDropdown;
    EditText edit_marca, edit_activo, edit_dosis, edit_precio;

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

        String[] COUNTRIES = new String[] {"Jarabe", "Suspensión","Emulsión", "Gotas", "Bebible", "Inyectable", "Comprimidos", "Grageas",
                "Efervescentes", "Cápsulas", "Tabletas", "Pomada", "Gel", "Crema"};

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        getContext(),
                        R.layout.forma_farmaceutica_item,
                        COUNTRIES);

        editTextFilledExposedDropdown = view.findViewById(R.id.filled_exposed_dropdown);
        editTextFilledExposedDropdown.setAdapter(adapter);

        edit_activo = view.findViewById(R.id.editText_activo);
        edit_dosis = view.findViewById(R.id.editText_dosis);
        edit_marca = view.findViewById(R.id.editText_marca);
        edit_precio = view.findViewById(R.id.editText_precio);


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
                Bundle bundle = new Bundle();
                bundle.putString("activo", edit_activo.getText().toString());
                bundle.putString("dosis", edit_dosis.getText().toString());
                bundle.putString("marca", edit_marca.getText().toString());
                bundle.putString("precio", edit_precio.getText().toString());
                bundle.putString("presentacion", editTextFilledExposedDropdown.getText().toString());
                navController.navigate(R.id.action_nuevoMedicamento_to_bottomDialogConfirmaMedicamento, bundle);
                break;
        }
    }
}