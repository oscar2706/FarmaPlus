package com.example.farmaplus.client;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.farmaplus.LogginHandler;
import com.example.farmaplus.R;

public class PrincipalCliente extends Fragment implements View.OnClickListener {
    Button button_NuevoPedido;
    //NavController para navegar
    NavController navController;

    public PrincipalCliente() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        //
        navController = Navigation.findNavController(getActivity(), R.id.fragment_navigation);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_principal_cliente, container, false);
        button_NuevoPedido = view.findViewById(R.id.button_NuevoPedido);
        button_NuevoPedido.setOnClickListener(this);
        view.findViewById(R.id.cardView_Historial).setOnClickListener(this);
        view.findViewById(R.id.cardView_Medicamentos).setOnClickListener(this);
        view.findViewById(R.id.cardView_PedidoActual).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_NuevoPedido:
                navController.navigate(R.id.action_principalCliente_to_nuevoPedido);
                break;
            case R.id.cardView_PedidoActual:
                navController.navigate(R.id.action_principalCliente_to_pedidoActual);
                break;
            case R.id.cardView_Historial:
                navController.navigate(R.id.action_principalCliente_to_historialPedidos);
                break;
            case R.id.cardView_Medicamentos:
                navController.navigate(R.id.action_principalCliente_to_medicamentos);
                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_direcciones:
                navController.navigate(R.id.action_principalCliente_to_direcciones);
                break;
            case R.id.menu_logout:
                navController.navigate(R.id.login_fragment);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void redirectIfNotLoggedIn() {
        if(!LogginHandler.isLoggedIn(getActivity())){
            NavController navController = Navigation.findNavController(getActivity(), R.id.fragment_navigation);
            navController.navigate(R.id.login_fragment) ;
        }
    }
}