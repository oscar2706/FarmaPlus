package com.example.farmaplus.pharmacy;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.example.farmaplus.R;
import com.example.farmaplus.Repartidor;
import com.example.farmaplus.client.Pedido;
import com.example.farmaplus.client.PrincipalCliente;
import com.example.farmaplus.client.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.Date;

public class BottomDialogConfirmaNuevoRepartidor extends BottomSheetDialogFragment implements View.OnClickListener {

    Button btn_aceptar, btn_cancel;
    DatabaseReference reference;
    DatabaseReference reference2;
    ProgressDialog progressDialog;
    Repartidor repartidor;
    User user;

    Uri uri_img;
    public BottomDialogConfirmaNuevoRepartidor() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_dialog_confirma_registra_repartidor, container, false);
        btn_aceptar = view.findViewById(R.id.buttonAceptar);
        btn_cancel = view.findViewById(R.id.buttonCancelar);
        btn_cancel.setOnClickListener(this);
        btn_aceptar.setOnClickListener(this);

        progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);

        FirebaseDatabase database =  FirebaseDatabase.getInstance();
        reference = database.getReference("REPARTIDOR");
        reference2 = database.getReference("USER");

        try {
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);

            String fecha = String.valueOf(mDay)+"/"+String.valueOf(mMonth+1)+"/"+String.valueOf(mYear);
            String nom = getArguments().getString("nombreC");
            String usuario = getArguments().getString("usuario");
            String contr = getArguments().getString("contra");
            uri_img = Uri.parse(getArguments().getString("url"));

            repartidor = new Repartidor();
            user = new User();

            repartidor.setFechaRegistro(fecha);
            repartidor.setNombre(nom);
            repartidor.setUsuario(usuario);
            repartidor.setPassword(contr);

            user.setCorreo(usuario);
            user.setPassword(contr);

        }catch (Exception e){

        }
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonAceptar:
                progressDialog.setMessage("REGISTRANDO NUEVO REPARTIDOR");
                progressDialog.show();

                StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                StorageReference folderRef = storageReference.child("REPARTIDORES_IMG");
                StorageReference fotoRef = folderRef.child(new Date().toString());

                fotoRef.putFile(uri_img).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while(!uriTask.isSuccessful());
                        Uri downloadUri = uriTask.getResult();
                        repartidor.setFotoRepartidor(downloadUri.toString());

                        //INSERT OBJETO PEDIDO FIREBASE
                        reference.push().setValue(repartidor);
                        reference2.push().setValue(user);

                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Repartidor Registrado Con Exito", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(getActivity(), R.id.fragment_navigation).navigate(
                                R.id.action_bottomDialogConfirmaNuevoRepartidor_to_repartidoresFragment);
                    }
                });
                break;
            case R.id.buttonCancelar:
                dismiss();
                break;
        }

    }
}
