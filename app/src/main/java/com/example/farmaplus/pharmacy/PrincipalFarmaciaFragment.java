package com.example.farmaplus.pharmacy;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.farmaplus.R;

public class PrincipalFarmaciaFragment extends Fragment implements View.OnClickListener {
    NavController navController;

    //CONSTANTES DE ACCION
    public static final int CODE_CAMERA = 21;
    public static final int CODE_GALLERY = 22;
    Fragment np;

    public PrincipalFarmaciaFragment() {
    }

    public static PrincipalFarmaciaFragment newInstance(String param1, String param2) {
        PrincipalFarmaciaFragment fragment = new PrincipalFarmaciaFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        np = new NuevoRepartidorFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.farmacia_principal_farmacia, container, false);
        CardView cardNuevosPedidos = view.findViewById(R.id.cardView_NuevosPedidos);
        CardView cardPedidosEnCurso = view.findViewById(R.id.cardView_PedidosEnCurso);
        CardView cardHistorialPedidos = view.findViewById(R.id.cardView_HistorialDePedidos);
        CardView cardMedicamentos = view.findViewById(R.id.cardView_Medicamentos);
        CardView cardRepartidores = view.findViewById(R.id.cardView_Repartidores);
        cardNuevosPedidos.setOnClickListener(this);
        cardPedidosEnCurso.setOnClickListener(this);
        cardHistorialPedidos.setOnClickListener(this);
        cardMedicamentos.setOnClickListener(this);
        cardRepartidores.setOnClickListener(this);
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
            case R.id.cardView_NuevosPedidos:
                navController.navigate(R.id.action_principalFarmacia_to_pedidosNuevos);
                break;
            case R.id.cardView_PedidosEnCurso:
                navController.navigate(R.id.action_principalFarmacia_to_pedidosEnCurso);
                break;
            case R.id.cardView_HistorialDePedidos:
                navController.navigate(R.id.action_principalFarmacia_to_historialPedidos);
                break;
            case R.id.cardView_Medicamentos:
                navController.navigate(R.id.action_principalFarmacia_to_medicamentos);
                break;
            case R.id.cardView_Repartidores:
                navController.navigate(R.id.action_principalFarmacia_to_repartidores);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case CODE_GALLERY:
                np.onActivityResult(requestCode, resultCode, data);
                break;

            case CODE_CAMERA:
                np.onActivityResult(requestCode, resultCode, data);
                break;

        }
    }
}