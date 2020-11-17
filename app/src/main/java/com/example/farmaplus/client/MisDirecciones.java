package com.example.farmaplus.client;

import android.app.ActionBar;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.farmaplus.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MisDirecciones extends Fragment{
    RecyclerView rc;

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

        rc = view.findViewById(R.id.rcDirecc);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        lm.setOrientation(RecyclerView.VERTICAL);
        rc.setLayoutManager(lm);

        DireccionAdapter adapter = new DireccionAdapter(DireccionService.direcciones, R.layout.item_direccion, getActivity());

        rc.setAdapter(adapter);
        cargaDatosFire();


        buttonAddDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomDialog bottomDialog_addAddress = new BottomDialog();
                bottomDialog_addAddress.show(getFragmentManager(), "");
            }
        });
        return view;
    }

    private void cargaDatosFire() {
        final FirebaseDatabase database =  FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("DIRECCION");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Direccion direccion = snapshot.getValue(Direccion.class);

                direccion.setIdDom(snapshot.getKey());

                if(!DireccionService.direcciones.contains(direccion)) {
                    if(direccion.getIdUser().equals(PrincipalCliente.idUser))
                        DireccionService.addDireccion(direccion);
                }
                rc.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Direccion direccion = snapshot.getValue(Direccion.class);
                direccion.setIdDom(snapshot.getKey());

                if(DireccionService.direcciones.contains(direccion)) {
                    DireccionService.actualizarStatus(direccion);
                }
                rc.getAdapter().notifyDataSetChanged();
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
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }
}