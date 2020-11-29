package com.example.farmaplus.pharmacy;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.farmaplus.R;
import com.example.farmaplus.client.Pedido;
import com.example.farmaplus.client.PedidoAdapterClic;
import com.example.farmaplus.client.PedidoService;
import com.example.farmaplus.client.PrincipalCliente;
import com.google.android.material.chip.Chip;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class PedidosEnCursoFragment extends Fragment implements View.OnClickListener {
    Chip btn_todos, btn_sucursal, btn_domicilio;
    RecyclerView rc;
    NavController navController;

    public PedidosEnCursoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        View view = inflater.inflate(R.layout.farmacia_pedidos_en_curso, container, false);
        btn_todos = view.findViewById(R.id.chip4);
        btn_sucursal = view.findViewById(R.id.chip5);
        btn_domicilio = view.findViewById(R.id.chip6);
        btn_todos.setOnClickListener(this);
        btn_domicilio.setOnClickListener(this);
        btn_sucursal.setOnClickListener(this);

        rc = view.findViewById(R.id.rc_ped);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        lm.setOrientation(RecyclerView.VERTICAL);
        rc.setLayoutManager(lm);

        // PedidoAdapter adapter = new PedidoAdapter(PedidoService.pedidos, R.layout.item_pedido, getActivity());
        PedidoAdapterEnCurso adapter = new PedidoAdapterEnCurso(PedidoService.pedidos, R.layout.item_pedido_en_curso, getActivity());

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = PedidoService.pedidos.get(rc.getChildAdapterPosition(view)).getId();
                Bundle bundle = new Bundle();
                bundle.putString("idP", id);
                navController.navigate(R.id.action_pedidosEnCursoFragment_to_farmaciaPedidoEncursoDetalle, bundle);
            }
        });
        rc.setAdapter(adapter);
        cargaTodosPedidos();

        return view;
    }

    private void cargaTodosPedidos() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("PEDIDO");
        Query query = reference.orderByChild("enCurso").equalTo("true");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //   Pedido p = snapshot.getValue(Pedido.class);
                PedidoService.vaciarLista();

                for (DataSnapshot ds: snapshot.getChildren()){
                    Pedido p = ds.getValue(Pedido.class);
                        p.setId(ds.getKey());
                        //   if(p.getUser().equals(PrincipalActivity.user))
                        PedidoService.addPedido(p);
                }

                rc.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void cargaEnSucursalPedidos() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("PEDIDO");
        Query query = reference.orderByChild("enCurso").equalTo("true");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //   Pedido p = snapshot.getValue(Pedido.class);
                PedidoService.vaciarLista();

                for (DataSnapshot ds: snapshot.getChildren()){
                    Pedido p = ds.getValue(Pedido.class);
                    if(p.getTipoEntrega().equals("En Sucursal")) {
                        p.setId(ds.getKey());
                        //   if(p.getUser().equals(PrincipalActivity.user))
                        PedidoService.addPedido(p);
                        Log.e("info", "================>" + p.getComentarios());
                        //  Toast.makeText(getActivity(), p.getComentarios()+"-"+p.getEstadoPedido(), Toast.LENGTH_SHORT).show();
                    }
                }

                rc.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void cargaDomicilioPedidos() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("PEDIDO");
        Query query = reference.orderByChild("enCurso").equalTo("true");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //   Pedido p = snapshot.getValue(Pedido.class);
                PedidoService.vaciarLista();

                for (DataSnapshot ds: snapshot.getChildren()){
                    Pedido p = ds.getValue(Pedido.class);
                    if(p.getTipoEntrega().equals("Domicilio")) {
                        p.setId(ds.getKey());
                        //   if(p.getUser().equals(PrincipalActivity.user))
                        PedidoService.addPedido(p);
                        Log.e("info", "================>" + p.getComentarios());
                        //  Toast.makeText(getActivity(), p.getComentarios()+"-"+p.getEstadoPedido(), Toast.LENGTH_SHORT).show();
                    }
                }

                rc.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //Boton todos
            case R.id.chip4:
                cargaTodosPedidos();
                break;
            //Boton Sucursal
            case R.id.chip5:
                cargaEnSucursalPedidos();
                break;
            //Boton Domicilio
            case R.id.chip6:
                cargaDomicilioPedidos();
                break;
        }

    }
}