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

public class BottomDialogConfirmaMedicamento extends BottomSheetDialogFragment implements View.OnClickListener {
    Button btn_aceptar, btn_cancel;
    DatabaseReference reference;

    ProgressDialog progressDialog;

    Medicamento medicamento;

    public BottomDialogConfirmaMedicamento() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_dialog_confirma_medicamento, container, false);
        btn_aceptar = view.findViewById(R.id.buttonAceptar);
        btn_cancel = view.findViewById(R.id.buttonCancelar);
        btn_cancel.setOnClickListener(this);
        btn_aceptar.setOnClickListener(this);

        progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);

        FirebaseDatabase database =  FirebaseDatabase.getInstance();
        reference = database.getReference("MEDICAMENTO");

        try {

            String marca = getArguments().getString("marca");
            String dosis = getArguments().getString("dosis");
            String principioActivo = getArguments().getString("activo");
            String forma = getArguments().getString("presentacion");
            String precio = getArguments().getString("precio");

            medicamento = new Medicamento();

            medicamento.setMarca(marca);
            medicamento.setDosis(dosis);
            medicamento.setPrincipioActivo(principioActivo);
            medicamento.setFormaFarmaceutica(forma);
            medicamento.setPrecio(precio);
        }catch (Exception e){

        }

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonAceptar:
                progressDialog.setMessage("REGISTRANDO NUEVO MEDICAMENTO");
                progressDialog.show();

                reference.push().setValue(medicamento);

                progressDialog.dismiss();
                dismiss();
                Toast.makeText(getActivity(), "Medicamento Registrado Con Exito", Toast.LENGTH_SHORT).show();
                break;
            case R.id.buttonCancelar:
                dismiss();
                break;
        }

    }
}

