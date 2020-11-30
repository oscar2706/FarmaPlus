package com.example.farmaplus.client;

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
import com.example.farmaplus.pharmacy.Medicamento;
import com.example.farmaplus.pharmacy.MedicamentoAdapterClic;
import com.example.farmaplus.pharmacy.MedicamentoService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Medicamentos extends Fragment {
    RecyclerView rc;
    NavController navController;

    public Medicamentos() {
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
        View view = inflater.inflate(R.layout.fragment_medicamentos, container, false);

        rc = view.findViewById(R.id.rc);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        lm.setOrientation(RecyclerView.VERTICAL);
        rc.setLayoutManager(lm);

        // PedidoAdapter adapter = new PedidoAdapter(PedidoService.pedidos, R.layout.item_pedido, getActivity());
        MedicamentoAdapterClic adapter = new MedicamentoAdapterClic(MedicamentoService.medicamentos, R.layout.item_medicamento, getActivity());

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = MedicamentoService.medicamentos.get(rc.getChildAdapterPosition(view)).getIdMedicamento();
                Bundle bundle = new Bundle();
                bundle.putString("idP", id);
                navController.navigate(R.id.action_medicamentos_to_detalleMedicamento, bundle);
            }
        });
        rc.setAdapter(adapter);
        cargaDatosFire();
        return view;
    }

    private void cargaDatosFire() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("MEDICAMENTO");
        Query query = reference.orderByChild("marca");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //   Pedido p = snapshot.getValue(Pedido.class);
                MedicamentoService.vaciarLista();

                for (DataSnapshot ds: snapshot.getChildren()){
                    Medicamento p = ds.getValue(Medicamento.class);
                    p.setIdMedicamento(ds.getKey());
                    //   if(p.getUser().equals(PrincipalActivity.user))
                    MedicamentoService.addMedicamento(p);
                    Log.e("info", "================>" + p.getMarca());
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