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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.farmaplus.R;
import com.example.farmaplus.Repartidor;
import com.example.farmaplus.client.Direccion;
import com.example.farmaplus.client.DireccionService;
import com.example.farmaplus.client.PrincipalCliente;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class PrincipalRepartidorFragment extends Fragment implements View.OnClickListener {
    NavController navController;
    public static String idRepartidor;
     String correo;
     TextView txt_user;
     public static String nombreRep;
    public static String uriFoto;
     ImageView fotoRep;




    public PrincipalRepartidorFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        try {
            correo = getArguments().getString("user");
            validaRepartidor();
        }catch (Exception e){}
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
        txt_user = view.findViewById(R.id.textView24);
        fotoRep = view.findViewById(R.id.imageView_avatar);

        try {
            txt_user.setText("Bienvenido "+nombreRep);
            Glide.with(getActivity()).load(uriFoto).into(fotoRep);

        }catch (Exception e){}

        return view;
    }

    private void validaRepartidor() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("REPARTIDOR");
        Query query = reference.orderByChild("usuario").equalTo(correo);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //   Pedido p = snapshot.getValue(Pedido.class);
                DireccionService.vaciarLista();

                for (DataSnapshot ds: snapshot.getChildren()){
                    Repartidor rep = ds.getValue(Repartidor.class);
                    rep.setIdRepartidor(ds.getKey());

                    idRepartidor = rep.getIdRepartidor();
                    String string = rep.getNombre();
                    String[] parts = string.split(" ");
                    String nombre = parts[0];

                    nombreRep = nombre;
                    uriFoto = rep.getFotoRepartidor();

                   txt_user.setText("Bienvenido "+nombre);
                    Glide.with(getActivity()).load(rep.getFotoRepartidor()).into(fotoRep);
                    //Toast.makeText(getContext(), idRepartidor, Toast.LENGTH_LONG).show();
                }

                //   rc.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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