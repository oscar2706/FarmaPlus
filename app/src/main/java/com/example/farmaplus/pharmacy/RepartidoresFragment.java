package com.example.farmaplus.pharmacy;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.farmaplus.R;
import com.example.farmaplus.Repartidor;
import com.example.farmaplus.client.Direccion;
import com.example.farmaplus.client.DireccionAdapter;
import com.example.farmaplus.client.DireccionService;
import com.example.farmaplus.client.PrincipalCliente;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RepartidoresFragment extends Fragment implements View.OnClickListener {
    RecyclerView rc;
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

        rc = view.findViewById(R.id.rcRep);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        lm.setOrientation(RecyclerView.VERTICAL);
        rc.setLayoutManager(lm);

        RepartidorAdapter adapter = new RepartidorAdapter(RepartidorService.repartidores, R.layout.item_seleccion_repartidor, getActivity());

        rc.setAdapter(adapter);
        cargaDatosFire();
        return view;
    }

    private void cargaDatosFire() {
        final FirebaseDatabase database =  FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("REPARTIDOR");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Repartidor repartidor = snapshot.getValue(Repartidor.class);

                repartidor.setIdRepartidor(snapshot.getKey());

                if(!RepartidorService.repartidores.contains(repartidor)) {
                        RepartidorService.addRepartidor(repartidor);
                }
                rc.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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