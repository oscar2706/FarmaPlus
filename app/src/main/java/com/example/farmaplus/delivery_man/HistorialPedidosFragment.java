package com.example.farmaplus.delivery_man;

import android.graphics.Color;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.farmaplus.R;
import com.example.farmaplus.client.DireccionAdapterClic;
import com.example.farmaplus.client.DireccionService;
import com.example.farmaplus.client.Pedido;
import com.example.farmaplus.client.PedidoAdapter;
import com.example.farmaplus.client.PedidoAdapterClic;
import com.example.farmaplus.client.PedidoService;
import com.example.farmaplus.client.PrincipalCliente;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class HistorialPedidosFragment extends Fragment {
    RecyclerView rc;
    NavController navController;

    public HistorialPedidosFragment() {
        // Required empty public constructor
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
        View view = inflater.inflate(R.layout.repartidor_historial_pedidos, container, false);

        rc = view.findViewById(R.id.rc);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        lm.setOrientation(RecyclerView.VERTICAL);
        rc.setLayoutManager(lm);

      //  PedidoAdapter adapter = new PedidoAdapter(PedidoService.pedidos, R.layout.item_pedido, getActivity());
        PedidoAdapterClic adapter = new PedidoAdapterClic(PedidoService.pedidos, R.layout.item_pedido, getActivity());

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = PedidoService.pedidos.get(rc.getChildAdapterPosition(view)).getId();
                Bundle bundle = new Bundle();
                bundle.putString("idP", id);
                navController.navigate(R.id.action_historialPedidosRepartidor_to_repartidor_detalle_pedido, bundle);
            }
        });

        rc.setAdapter(adapter);
        cargaDatosFire();
        return view;
    }

    private void cargaDatosFire() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("PEDIDO");
        Query query = reference.orderByChild("idRepartidor").equalTo(PrincipalRepartidorFragment.idRepartidor);
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
               //     Log.e("info", "================>"+p.getComentarios());
                    //  Toast.makeText(getActivity(), p.getComentarios()+"-"+p.getEstadoPedido(), Toast.LENGTH_SHORT).show();
                }

                rc.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}