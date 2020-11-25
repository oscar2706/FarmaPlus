package com.example.farmaplus.client;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.farmaplus.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class PedidoActual extends Fragment {
    Group group_sinPedido;
    Group group_conPedido;

    ImageView imgReceta;
    ProgressBar progreso;
    TextView txt_estado, txt_dirección, txt_repartidor, txt_com, txt_id;

    public PedidoActual() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pedido_actual, container, false);
        group_sinPedido = view.findViewById(R.id.group_sinPedido);
        group_conPedido = view.findViewById(R.id.group_pedido);

        imgReceta = view.findViewById(R.id.imageView_receta);
        progreso = view.findViewById(R.id.progressBar);
        txt_estado = view.findViewById(R.id.textView5);
        txt_dirección = view.findViewById(R.id.textView7);
        txt_repartidor = view.findViewById(R.id.textView10);
        txt_com = view.findViewById(R.id.textView12);
        txt_id = view.findViewById(R.id.textView2);

        if(PrincipalCliente.enCurso == true){
            group_sinPedido.setVisibility(View.GONE);
        }else {
            group_conPedido.setVisibility(View.GONE);
        }
        cargarPedidoEnCurso();
        return view;
    }

    private void cargarPedidoEnCurso() {
        final FirebaseDatabase database =  FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("PEDIDO");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Pedido pedido = snapshot.getValue(Pedido.class);

                pedido.setId(snapshot.getKey());

                if(pedido.getUser().equals(PrincipalCliente.idUser)){
                //    System.out.println("Pedidos: "+pedido.getEnCurso());
                    if(pedido.getEnCurso().equals("true")){
                      //  enCurso = true;
                        txt_id.setText("Id Pedido: "+pedido.getId());
                        txt_com.setText(pedido.getComentarios());
                        txt_dirección.setText(pedido.getLugarEntrega());
                        txt_estado.setText(pedido.getEstadoPedido());
                        txt_repartidor.setText(pedido.getRepartidor());
                        if(getView() != null) {
                            Glide.with(getActivity()).load(pedido.getUrl()).into(imgReceta);
                        }

                        if(pedido.getTipoEntrega().equals("Domicilio"))
                        {
                            switch (pedido.getEstadoPedido())
                            {
                                case "Enviado":
                                    progreso.setProgress(17);
                                    break;
                                case "Recibido":
                                    progreso.setProgress(34);
                                    break;
                                case "En Preparacion":
                                    progreso.setProgress(51);
                                    break;
                                case "Repartidor Asignado":
                                    progreso.setProgress(68);
                                    break;
                                case "En Camino":
                                    progreso.setProgress(85);
                                    break;
                                case "Entregado":
                                    progreso.setProgress(100);
                                    break;
                            }
                        }
                        else
                        {
                            switch (pedido.getEstadoPedido())
                            {
                                case "Enviado":
                                    progreso.setProgress(20);
                                    break;
                                case "Recibido":
                                    progreso.setProgress(40);
                                    break;
                                case "En Preparacion":
                                    progreso.setProgress(60);
                                    break;
                                case "Listo en Sucursal":
                                    progreso.setProgress(80);
                                    break;
                                case "Entregado":
                                    progreso.setProgress(100);
                                    break;
                            }
                        }
                    }
                }


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Pedido pedido = snapshot.getValue(Pedido.class);

                txt_estado.setText(pedido.getEstadoPedido());
                txt_repartidor.setText(pedido.getRepartidor());

                if(pedido.getTipoEntrega().equals("Domicilio"))
                {
                    switch (pedido.getEstadoPedido())
                    {
                        case "Enviado":
                            progreso.setProgress(17);
                            break;
                        case "Recibido":
                            progreso.setProgress(34);
                            break;
                        case "En Preparacion":
                            progreso.setProgress(51);
                            break;
                        case "Repartidor Asignado":
                            progreso.setProgress(68);
                            break;
                        case "En Camino":
                            progreso.setProgress(85);
                            break;
                        case "Entregado":
                            PrincipalCliente.enCurso = false;
                            progreso.setProgress(100);
                            break;
                    }
                }
                else
                {
                    switch (pedido.getEstadoPedido())
                    {
                        case "Enviado":
                            progreso.setProgress(20);
                            break;
                        case "Recibido":
                            progreso.setProgress(40);
                            break;
                        case "En Preparacion":
                            progreso.setProgress(60);
                            break;
                        case "Listo en Sucursal":
                            progreso.setProgress(80);
                            break;
                        case "Entregado":
                            PrincipalCliente.enCurso = false;
                            progreso.setProgress(100);
                            break;
                    }
                }

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
}