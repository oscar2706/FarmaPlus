package com.example.farmaplus.pharmacy;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmaplus.R;
import com.example.farmaplus.Repartidor;
import com.example.farmaplus.client.Pedido;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class BottomDialogSeleccionaRepartidor extends BottomSheetDialogFragment implements View.OnClickListener {
    Button buttonAceptar, buttonCancelar;
    RecyclerView rc;
    TextView txt_repa, txt_idRepa;
    Pedido pedido;
    String idp;

    int row_index = -1;
    View vAnterior;
    TextView texto;

    public BottomDialogSeleccionaRepartidor() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_dialog_selecciona_repartidor, container, false);
        buttonAceptar = view.findViewById(R.id.buttonAceptar2);
        buttonCancelar = view.findViewById(R.id.buttonCancelar2);
        buttonAceptar.setOnClickListener(this);
        buttonCancelar.setOnClickListener(this);
        txt_repa = view.findViewById(R.id.textviewDirecc);
        txt_idRepa = view.findViewById(R.id.txt_idRepa);

        try {
            idp = getArguments().getString("idP");
        }catch (Exception e){

        }

        rc = view.findViewById(R.id.recyclerView);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        lm.setOrientation(RecyclerView.VERTICAL);
        rc.setLayoutManager(lm);

        RepartidorAdapterClic adapter = new RepartidorAdapterClic(RepartidorService.repartidores, R.layout.item_seleccion_repartidor, getActivity());

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    vAnterior.findViewById(R.id.card_suc).setBackgroundColor(Color.parseColor("#ffffff"));
                    texto =(TextView) vAnterior.findViewById(R.id.textView35);
                    texto.setTextColor(Color.parseColor("#212121"));
                }catch (Exception e)
                { }

                txt_repa.setText( RepartidorService.repartidores.get(rc.getChildAdapterPosition(view)).getNombre());
                txt_idRepa.setText( RepartidorService.repartidores.get(rc.getChildAdapterPosition(view)).getIdRepartidor());
                view.findViewById(R.id.card_suc).setBackgroundColor(Color.parseColor("#212121"));
                texto = (TextView)  view.findViewById(R.id.textView35);
                texto.setTextColor(Color.parseColor("#ffffff"));

                vAnterior = view;

                //  holder.tv1.setTextColor(Color.parseColor("#000000"))
            }
        });

        rc.setAdapter(adapter);
        cargaDatosFire();

        return view;
    }

    private void cargaDatosFire() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("REPARTIDOR");
        Query query = reference.orderByChild("nombre");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //   Pedido p = snapshot.getValue(Pedido.class);
                RepartidorService.vaciarLista();

                for (DataSnapshot ds: snapshot.getChildren()){
                    Repartidor repartidor = ds.getValue(Repartidor.class);
                    repartidor.setIdRepartidor(ds.getKey());

                    if(!RepartidorService.repartidores.contains(repartidor)) {
                        RepartidorService.addRepartidor(repartidor);
                        rc.getAdapter().notifyDataSetChanged();
                    }

                    //   rc.getAdapter().notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonAceptar2:
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference("PEDIDO");

                HashMap hashMap = new HashMap();
                hashMap.put("estadoPedido", "Repartidor Asignado");
                hashMap.put("repartidor", txt_repa.getText().toString());
                hashMap.put("idRepartidor", txt_idRepa.getText().toString());

                reference.child(idp).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        //    progressDialog.dismiss();
                        Toast.makeText(getContext(), "REPARTIDOR ASIGNADO CORRECTAMENTE", Toast.LENGTH_LONG).show();
                        Navigation.findNavController(getActivity(), R.id.fragment_navigation).navigate(
                                R.id.action_bottomDialogSeleccionaRepartidor_to_principalFarmacia);
                    }
                });
                break;

            case R.id.buttonCancelar2:
                dismiss();
                break;

        }
    }
}
