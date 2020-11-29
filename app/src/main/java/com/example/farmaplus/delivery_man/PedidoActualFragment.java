package com.example.farmaplus.delivery_man;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.farmaplus.R;
import com.example.farmaplus.client.Direccion;
import com.example.farmaplus.client.Pedido;
import com.example.farmaplus.client.PedidoService;
import com.example.farmaplus.client.PrincipalCliente;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import static android.Manifest.permission.CALL_PHONE;

public class PedidoActualFragment extends Fragment implements View.OnClickListener {
    NavController navController;

    ImageView imgReceta;
    TextView txt_estado, txt_dirección, txt_repartidor, txt_com, txt_id;

    String direccion, idP;
    String latitud, longitud;


    public PedidoActualFragment() {
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
        View view = inflater.inflate(R.layout.repartidor_pedido_actual, container, false);

        Button button_entregaPedido = view.findViewById(R.id.button_entregaPedido);
        button_entregaPedido.setOnClickListener(this);
        Button button_ruta = view.findViewById(R.id.button6);
        button_ruta.setOnClickListener(this);
        Button button_llamar = view.findViewById(R.id.btn_llamar);
        button_llamar.setOnClickListener(this);

        imgReceta = view.findViewById(R.id.imageView_fotoReceta);
        txt_dirección = view.findViewById(R.id.textView23);
        txt_com = view.findViewById(R.id.textView22);
        txt_id = view.findViewById(R.id.textView19);


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


                if(pedido.getIdRepartidor().equals(PrincipalRepartidorFragment.idRepartidor)){
                    //    System.out.println("Pedidos: "+pedido.getEnCurso());
                    if(pedido.getEnCurso().equals("true")){
                        //  enCurso = true;
                        idP = pedido.getId();
                        txt_id.setText("Id Pedido: "+pedido.getId());
                        txt_com.setText(pedido.getComentarios());
                        direccion = pedido.getLugarEntrega();
                        txt_dirección.setText(pedido.getLugarEntrega());

                        if(getView() != null) {
                            Glide.with(getActivity()).load(pedido.getUrl()).into(imgReceta);
                        }

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference reference = database.getReference("PEDIDO");

                        HashMap hashMap = new HashMap();
                            hashMap.put("estadoPedido", "En Camino");

                        reference.child(idP).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                //    progressDialog.dismiss();
                            }
                        });

                        BuscaCoordenadas();
                    }
                }


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Pedido pedido = snapshot.getValue(Pedido.class);

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
    public void onClick(View view) {
        Uri uri = null;
        Intent intent = null;

        switch (view.getId()){
            case R.id.button_entregaPedido:
                Bundle bundle = new Bundle();
                bundle.putString("idP", idP);
                navController.navigate(R.id.action_pedidoActualRepartidor_to_bottomDialogConfirmaEntrega, bundle);
                break;
            case R.id.button6:
                String direc = "geo:"+latitud+","+longitud+"?z=16&q="+latitud+","+longitud+"(Entrega)";
             //   String d = "geo:0,0?q="+direccion;
                uri = Uri.parse(direc);
                intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case R.id.btn_llamar:
                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:2225790653"));
                if (ContextCompat.checkSelfPermission(getContext(), CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    startActivity(i);
                } else {
                    requestPermissions(new String[]{CALL_PHONE}, 1);
                }
           //     startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:2225790663")));
                break;
        }
    }

    public void BuscaCoordenadas()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("DIRECCION");
        Query query = reference.orderByChild("direccion").equalTo(direccion);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //   Pedido p = snapshot.getValue(Pedido.class);

                for (DataSnapshot ds: snapshot.getChildren()){
                    Direccion p = ds.getValue(Direccion.class);
                    p.setIdDom(ds.getKey());
                    latitud = p.getLatitud();
                    longitud = p.getLongitud();

                    //   if(p.getUser().equals(PrincipalActivity.user))

                    //     Log.e("info", "================>"+p.getComentarios());
                    //  Toast.makeText(getActivity(), p.getComentarios()+"-"+p.getEstadoPedido(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}