package com.example.farmaplus;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.farmaplus.client.Pedido;
import com.example.farmaplus.pharmacy.Medicamento;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class detalleMedicamento extends Fragment {
    TextView view_precio, view_marca, view_activo, view_dosis, view_forma;
    ImageView foto;


    public detalleMedicamento() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detalle_medicamento, container, false);

        String idP = getArguments().getString("idP");

        view_precio = view.findViewById(R.id.textView28);
        view_marca = view.findViewById(R.id.textView20);
        view_activo = view.findViewById(R.id.textView21);
        view_dosis = view.findViewById(R.id.textView24);
        view_forma = view.findViewById(R.id.textView26);
        foto = view.findViewById(R.id.imageView_fotoReceta);

        cargaDatosFire(idP);
        return  view;
    }

    private void cargaDatosFire(String idPedido) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("MEDICAMENTO");
        Query query = reference.orderByKey().equalTo(idPedido);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //   Pedido p = snapshot.getValue(Pedido.class);

                for (DataSnapshot ds: snapshot.getChildren()){
                    Medicamento m = ds.getValue(Medicamento.class);
                    m.setIdMedicamento(ds.getKey());

                    view_precio.setText("$ "+m.getPrecio());
                    view_marca.setText(m.getMarca());
                    view_activo.setText(m.getPrincipioActivo());
                    view_dosis.setText(m.getDosis());
                    view_forma.setText(m.getFormaFarmaceutica());

                    switch (m.getFormaFarmaceutica())
                    {
                        case "Jarabe":
                            foto.setImageResource(R.drawable.jarabe_emulsion);
                            break;
                        case "Suspensión":
                            foto.setImageResource(R.drawable.suspension);
                            break;
                        case "Emulsión":
                            foto.setImageResource(R.drawable.jarabe_emulsion);
                            break;
                        case "Gotas":
                            foto.setImageResource(R.drawable.gotas);
                            break;
                        case "Bebible":
                            foto.setImageResource(R.drawable.bebible);
                            break;
                        case "Inyectable":
                            foto.setImageResource(R.drawable.inyectable);
                            break;
                        case "Comprimidos":
                            foto.setImageResource(R.drawable.comprimidos);
                            break;
                        case "Grageas":
                            foto.setImageResource(R.drawable.grageas);
                            break;
                        case "Efervescentes":
                            foto.setImageResource(R.drawable.efervescentes);
                            break;
                        case "Cápsulas":
                            foto.setImageResource(R.drawable.capsulas);
                            break;
                        case "Tabletas":
                            foto.setImageResource(R.drawable.tabletas);
                            break;
                        case "Pomada":
                            foto.setImageResource(R.drawable.pomada_crema);
                            break;
                        case "Gel":
                            foto.setImageResource(R.drawable.gel);
                            break;
                        case "Crema":
                            foto.setImageResource(R.drawable.pomada_crema);
                            break;
                    }

                    //  Toast.makeText(getActivity(), p.getComentarios()+"-"+p.getEstadoPedido(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}