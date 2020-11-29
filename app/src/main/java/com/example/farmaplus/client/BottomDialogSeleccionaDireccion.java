package com.example.farmaplus.client;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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

import com.example.farmaplus.CustomToast;
import com.example.farmaplus.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;

public class BottomDialogSeleccionaDireccion extends BottomSheetDialogFragment implements View.OnClickListener {
    Button buttonAceptar, buttonCancelar;
    RecyclerView rc;
    TextView txt_direc;
    Pedido pedido;

    DatabaseReference reference;
    long maxId = 0;
    ProgressDialog progressDialog;

    View vAnterior;
    TextView texto;

    Uri uri_img;

    public BottomDialogSeleccionaDireccion() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_dialog_pedido_domicilio, container, false);
        buttonAceptar = view.findViewById(R.id.button_aceptar);
        buttonCancelar = view.findViewById(R.id.button_cancelar);
        buttonAceptar.setOnClickListener(this);
        buttonCancelar.setOnClickListener(this);
        txt_direc = view.findViewById(R.id.textviewDirecc);

        progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);

        FirebaseDatabase database =  FirebaseDatabase.getInstance();
        reference = database.getReference("PEDIDO");


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    maxId = (snapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        try {
            String com = getArguments().getString("com");
            String enC = getArguments().getString("enCurso");
            String estadoP = getArguments().getString("estadoPedido");
            String fechaP = getArguments().getString("fechaPedido");
            String rep = getArguments().getString("repartidor");
            String tipoE = getArguments().getString("tipoEntrega");
            String user = getArguments().getString("user");
            uri_img = Uri.parse(getArguments().getString("url"));

            pedido = new Pedido();

            pedido.setComentarios(com);
            pedido.setEnCurso(enC);
            pedido.setEstadoPedido(estadoP);
            pedido.setFechaPedido(fechaP);
            pedido.setRepartidor(rep);
            pedido.setTipoEntrega(tipoE);
            pedido.setUser(user);

        }catch (Exception e){

        }



        rc = view.findViewById(R.id.recyclerView_ubicacion);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        lm.setOrientation(RecyclerView.VERTICAL);
        rc.setLayoutManager(lm);

        DireccionAdapterClic adapter = new DireccionAdapterClic(DireccionService.direcciones, R.layout.item_direccion, getActivity());

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    vAnterior.findViewById(R.id.card_dir).setBackgroundColor(Color.parseColor("#ffffff"));
                    texto =(TextView) vAnterior.findViewById(R.id.txt_direccionItem);
                    texto.setTextColor(Color.parseColor("#212121"));
                    texto = (TextView) vAnterior.findViewById(R.id.txt_nombreDirecItem);
                    texto.setTextColor(Color.parseColor("#212121"));
                }catch (Exception e)
                { }
                txt_direc.setText( DireccionService.direcciones.get(rc.getChildAdapterPosition(view)).getDireccion());
                view.findViewById(R.id.card_dir).setBackgroundColor(Color.parseColor("#212121"));
                texto = (TextView)  view.findViewById(R.id.txt_direccionItem);
                texto.setTextColor(Color.parseColor("#ffffff"));
                texto =(TextView)    view.findViewById(R.id.txt_nombreDirecItem);
                texto.setTextColor(Color.parseColor("#ffffff"));

                vAnterior = view;

            }
        });

        rc.setAdapter(adapter);
        cargaDatosFire();
        return view;
    }

    private void cargaDatosFire() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("DIRECCION");
        Query query = reference.orderByChild("idUser").equalTo(PrincipalCliente.idUser);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //   Pedido p = snapshot.getValue(Pedido.class);
                DireccionService.vaciarLista();

                for (DataSnapshot ds: snapshot.getChildren()){
                    Direccion direccion = ds.getValue(Direccion.class);
                    direccion.setIdDom(ds.getKey());

                    if(!DireccionService.direcciones.contains(direccion)) {
                        if(direccion.getIdUser().equals(PrincipalCliente.idUser))
                            DireccionService.addDireccion(direccion);
                    }
                    rc.getAdapter().notifyDataSetChanged();
                }

             //   rc.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_cancelar:
              /*  Navigation.findNavController(getActivity(), R.id.fragment_navigation).navigate(
                        R.id.action_bottomDialogSeleccionaDireccion_to_nuevoPedido); */
                dismiss();
                break;
            case R.id.button_aceptar:
                progressDialog.setMessage("REALIZANDO PEDIDO");
                progressDialog.show();

                StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                StorageReference folderRef = storageReference.child("RECETAS_IMG");
                StorageReference fotoRef = folderRef.child(new Date().toString());

                fotoRef.putFile(uri_img).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        PrincipalCliente.enCurso = true;
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while(!uriTask.isSuccessful());
                        Uri downloadUri = uriTask.getResult();
                        pedido.setUrl(downloadUri.toString());
                        pedido.setLugarEntrega(txt_direc.getText().toString());
                        pedido.setIdRepartidor("sin asignar");

                        //INSERT OBJETO PEDIDO FIREBASE
                        reference.child(String.valueOf(maxId+1)).setValue(pedido);

                        progressDialog.dismiss();
                      //  Toast.makeText(getActivity(), "Pedido Recibido Por Farmacia", Toast.LENGTH_SHORT).show();
                        CustomToast.showOkToast(getActivity(), "Pedido Recibido");
                        Navigation.findNavController(getActivity(), R.id.fragment_navigation).navigate(
                        R.id.action_bottomDialogSeleccionaDireccion_to_principalCliente_fragment);
                    }
                });
                break;
        }
    }
}
