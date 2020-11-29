package com.example.farmaplus.pharmacy;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.farmaplus.R;
import com.example.farmaplus.client.Pedido;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class NuevoPedidoDetalleFragment extends Fragment implements View.OnClickListener {
    NavController navController;
    TextView txt_com, txt_fecha, txt_idp, txt_tipoEntrega;
    ImageView receta, imgDom;
    boolean dom;
    String idP;
    String img;

    public NuevoPedidoDetalleFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.farmacia_nuevo_pedido_detalle, container, false);
        Button button_aceptar = view.findViewById(R.id.buttonAceptar);
        button_aceptar.setOnClickListener(this);
        Button button_cancelar = view.findViewById(R.id.buttonCancelar);
        button_cancelar.setOnClickListener(this);

        idP = getArguments().getString("idP");

        txt_com = view.findViewById(R.id.textView22);
        txt_tipoEntrega = view.findViewById(R.id.textView23);
        txt_fecha = view.findViewById(R.id.textView25);
        txt_idp = view.findViewById(R.id.textView19);
        receta = view.findViewById(R.id.imageView_fotoReceta);
        imgDom = view.findViewById(R.id.imageView_sucursal2);

        receta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("url", img);
                navController.navigate(R.id.action_nuevoPedidoDetalleFragment_to_recetaDetalleImagen, bundle);
            }
        });

        cargaDatosFire(idP);

        return view;
    }

    private void cargaDatosFire(String idP) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("PEDIDO");
        Query query = reference.orderByKey().equalTo(idP);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //   Pedido p = snapshot.getValue(Pedido.class);

                for (DataSnapshot ds: snapshot.getChildren()){
                    Pedido p = ds.getValue(Pedido.class);
                    p.setId(ds.getKey());
                    txt_idp.setText("N° Pedido: "+p.getId());
                    txt_com.setText(p.getComentarios());
                    txt_tipoEntrega.setText(p.getTipoEntrega());
                    txt_fecha.setText(p.getFechaPedido());
                    img = p.getUrl();
                    Glide.with(getActivity()).load(p.getUrl()).into(receta);

                    if(p.getTipoEntrega().equals("En Sucursal")){
                        dom = false;
                        imgDom.setVisibility(View.GONE);
                    }else
                    {
                        dom = true;
                        imgDom.setVisibility(View.VISIBLE);
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        Navigation.setViewNavController(view, navController);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonAceptar:
                if(dom == true){
                    Bundle bundle = new Bundle();
                    bundle.putString("idP", idP);
                    navController.navigate(R.id.action_nuevoPedidoDetalleFragment_to_bottomDialogSeleccionaRepartidor, bundle);
                }else{
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference reference = database.getReference("PEDIDO");

                    HashMap hashMap = new HashMap();
                    hashMap.put("estadoPedido", "Listo en Sucursal");

                    reference.child(idP).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            //    progressDialog.dismiss();
                            Toast.makeText(getContext(), "PEDIDO RECIBIDO CORRECTAMENTE", Toast.LENGTH_LONG).show();
                            navController.navigate(R.id.action_nuevoPedidoDetalleFragment_to_principalFarmacia);
                        }
                    });
                }
                break;
            case R.id.buttonCancelar:
                Bundle bundle = new Bundle();
                bundle.putString("idP", idP);
                navController.navigate(R.id.action_nuevoPedidoDetalle_to_bottomDialogCancelaPedido, bundle);
                break;
        }
    }
}