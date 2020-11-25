package com.example.farmaplus.delivery_man;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.farmaplus.R;
import com.example.farmaplus.client.Pedido;
import com.example.farmaplus.client.PedidoService;
import com.example.farmaplus.client.PrincipalCliente;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class repartidor_detalle_pedido extends Fragment {
    TextView txt_com, txt_direccion, txt_repartidor, txt_fecha, txt_idp;
    ImageView receta;

    public repartidor_detalle_pedido() {
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
        View view = inflater.inflate(R.layout.repartidor_detalle_pedido, container, false);

        String idP = getArguments().getString("idP");

        txt_com = view.findViewById(R.id.textView22);
        txt_direccion = view.findViewById(R.id.textView23);
        txt_fecha = view.findViewById(R.id.textView27);
        txt_repartidor = view.findViewById(R.id.textView25);
        txt_idp = view.findViewById(R.id.textView19);
        receta = view.findViewById(R.id.imageView_fotoReceta);

        cargaDatosFire(idP);
        return view;
    }

    private void cargaDatosFire(String idPedido) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("PEDIDO");
        Query query = reference.orderByKey().equalTo(idPedido);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //   Pedido p = snapshot.getValue(Pedido.class);

                for (DataSnapshot ds: snapshot.getChildren()){
                    Pedido p = ds.getValue(Pedido.class);
                    p.setId(ds.getKey());
                    txt_idp.setText("Id Pedido: "+p.getId());
                    txt_com.setText(p.getComentarios());
                    txt_direccion.setText(p.getLugarEntrega());
                    txt_fecha.setText(p.getFechaPedido());
                    txt_repartidor.setText(p.getRepartidor());
                    Glide.with(getActivity()).load(p.getUrl()).into(receta);
                    //  Toast.makeText(getActivity(), p.getComentarios()+"-"+p.getEstadoPedido(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}