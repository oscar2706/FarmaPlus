package com.example.farmaplus.client;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmaplus.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
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

public class BottomDialogSleccionaSucursal extends BottomSheetDialogFragment implements View.OnClickListener {
    Button buttonAceptar, buttonCancelar;
    RecyclerView rc;
    TextView txt_direc;
    Pedido pedido;

    int row_index = -1;
    View vAnterior;
    TextView texto;

    DatabaseReference reference;
    long maxId = 0;
    ProgressDialog progressDialog;

    Uri uri_img;

    public BottomDialogSleccionaSucursal() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_dialog_pedido_sucursal, container, false);
        buttonAceptar = view.findViewById(R.id.button_aceptar);
        buttonCancelar = view.findViewById(R.id.button_cancelar);
        buttonAceptar.setOnClickListener(this);
        buttonCancelar.setOnClickListener(this);
        txt_direc = view.findViewById(R.id.textviewSucc);

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



        rc = view.findViewById(R.id.recyclerView_sucursal);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        lm.setOrientation(RecyclerView.VERTICAL);
        rc.setLayoutManager(lm);

        SucursalAdapterClic adapter = new SucursalAdapterClic(SucursalService.sucursales, R.layout.item_sucursal, getActivity());

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    vAnterior.findViewById(R.id.card_suc).setBackgroundColor(Color.parseColor("#ffffff"));
                    texto =(TextView) vAnterior.findViewById(R.id.textView16);
                    texto.setTextColor(Color.parseColor("#212121"));
                    texto = (TextView) vAnterior.findViewById(R.id.textView15);
                    texto.setTextColor(Color.parseColor("#212121"));
                }catch (Exception e)
                { }

                txt_direc.setText( SucursalService.sucursales.get(rc.getChildAdapterPosition(view)).getNombreSuc());
                view.findViewById(R.id.card_suc).setBackgroundColor(Color.parseColor("#212121"));
                texto = (TextView)  view.findViewById(R.id.textView16);
                texto.setTextColor(Color.parseColor("#ffffff"));
                texto =(TextView)    view.findViewById(R.id.textView15);
                texto.setTextColor(Color.parseColor("#ffffff"));

                vAnterior = view;

                    //  holder.tv1.setTextColor(Color.parseColor("#000000"))
            }
        });

        rc.setAdapter(adapter);
        cargaDatosFire();

        return view;
    }

    private void cargaDatosFire() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("SUCURSAL");
        Query query = reference.orderByChild("estado").equalTo("Puebla");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //   Pedido p = snapshot.getValue(Pedido.class);
                SucursalService.vaciarLista();

                for (DataSnapshot ds: snapshot.getChildren()){
                    Sucursal sucursal = ds.getValue(Sucursal.class);
                    sucursal.setIdSuc(ds.getKey());

                    if(!SucursalService.sucursales.contains(sucursal)) {
                            SucursalService.addSucursal(sucursal);
                    rc.getAdapter().notifyDataSetChanged();
                }

                //   rc.getAdapter().notifyDataSetChanged();
            }
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
            /*    Navigation.findNavController(getActivity(), R.id.fragment_navigation).navigate(
                        R.id.action_dialogSleccionaSucursal_to_nuevoPedido); */
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
                        Toast.makeText(getActivity(), "Pedido Recibido Por Farmacia", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(getActivity(), R.id.fragment_navigation).navigate(
                                R.id.action_bottomDialogSleccionaSucursal_to_principalCliente_fragment2);
                    }
                });
                break;
        }
    }
}
