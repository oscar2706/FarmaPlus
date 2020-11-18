package com.example.farmaplus.delivery_man;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.farmaplus.R;

public class PrincipalRepartidorFragment extends Fragment implements View.OnClickListener {
    NavController navController;

    public PrincipalRepartidorFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
        View view = inflater.inflate(R.layout.repartidor_principal, container, false);

        CardView cardView_pedidoEnCurso = view.findViewById(R.id.cardView_PedidoEnCurso);
        CardView cardView_pedidosPendientes = view.findViewById(R.id.cardView_PedidosPendientes);
        CardView cardView_historialPedidos = view.findViewById(R.id.cardView_HistoialPedidos);
        cardView_pedidoEnCurso.setOnClickListener(this);
        cardView_pedidosPendientes.setOnClickListener(this);
        cardView_historialPedidos.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.cardView_PedidoEnCurso:
                navController.navigate(R.id.action_principalRepartidor_to_pedidoActualRepartidor);
                break;
            case R.id.cardView_PedidosPendientes:
                navController.navigate(R.id.action_principalRepartidor_to_pedidosPendientesRepartidor);
                break;
            case R.id.cardView_HistoialPedidos:
                navController.navigate(R.id.action_principalRepartidor_to_historialPedidosRepartidor);
                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_repartidor, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        navController.navigate(R.id.login_fragment);
        return super.onOptionsItemSelected(item);
    }
}