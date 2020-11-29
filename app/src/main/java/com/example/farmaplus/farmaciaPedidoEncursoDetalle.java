package com.example.farmaplus;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.farmaplus.client.Pedido;
import com.example.farmaplus.pharmacy.PedidosEnCursoFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class farmaciaPedidoEncursoDetalle extends Fragment implements View.OnClickListener {
    TextView txt_com, txt_direccion, txt_repartidor, txt_fecha, txt_idp, txt_cambia;
    ImageView receta;
    Button btn_entrega;
    String idP;

    public farmaciaPedidoEncursoDetalle() {
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
        View view = inflater.inflate(R.layout.farmacia_pedidoencurso_detalle, container, false);

        idP = getArguments().getString("idP");

        txt_com = view.findViewById(R.id.textView22);
        txt_direccion = view.findViewById(R.id.textView23);
        txt_fecha = view.findViewById(R.id.textView27);
        txt_repartidor = view.findViewById(R.id.textView25);
        txt_idp = view.findViewById(R.id.textView19);
        txt_cambia = view.findViewById(R.id.textView24);
        receta = view.findViewById(R.id.imageView_fotoReceta);

        btn_entrega = view.findViewById(R.id.button_entregaPedido);
        btn_entrega.setOnClickListener(this);

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
                    Glide.with(getActivity()).load(p.getUrl()).into(receta);
                    txt_fecha.setText(p.getFechaPedido());
                    txt_com.setText(p.getComentarios());
                    txt_direccion.setText(p.getLugarEntrega());

                    if(p.getTipoEntrega().equals("Domicilio")){
                        txt_repartidor.setText(p.getRepartidor());
                        txt_cambia.setText("Repartidor");
                        btn_entrega.setVisibility(View.INVISIBLE);
                    }else if(p.getTipoEntrega().equals("En Sucursal")){
                        txt_repartidor.setText(p.getEstadoPedido());
                        txt_cambia.setText("Estado");
                    }


                    //  Toast.makeText(getActivity(), p.getComentarios()+"-"+p.getEstadoPedido(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.button_entregaPedido:
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference("PEDIDO");

                HashMap hashMap = new HashMap();
                hashMap.put("estadoPedido", "Entregado");
                hashMap.put("enCurso", "false");


                reference.child(idP).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Navigation.findNavController(getActivity(), R.id.fragment_navigation).navigate(
                                R.id.action_farmaciaPedidoEncursoDetalle_to_principalFarmacia);
                        CustomToast.showOkToastRepartidor(getActivity(), "Pedido entregado");
                    }
                });
                break;
        }
    }
}