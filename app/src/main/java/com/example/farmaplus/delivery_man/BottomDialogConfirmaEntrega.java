package com.example.farmaplus.delivery_man;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.example.farmaplus.CustomToast;
import com.example.farmaplus.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class BottomDialogConfirmaEntrega extends BottomSheetDialogFragment implements View.OnClickListener {
    String idP;
    public BottomDialogConfirmaEntrega() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_dialog_confirma_entrega, container, false);

        Button buttonAceptar, buttonCancelar;
        buttonAceptar = view.findViewById(R.id.buttonAceptar);
        buttonCancelar = view.findViewById(R.id.buttonCancelar);
        buttonAceptar.setOnClickListener(this);
        buttonCancelar.setOnClickListener(this);

        idP = getArguments().getString("idP");

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonAceptar:
            /*    progressDialog.setMessage("ACTUALIZANDO PEDIDO");
                progressDialog.show(); */
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference("PEDIDO");

                HashMap hashMap = new HashMap();
                hashMap.put("estadoPedido", "Entregado");
                hashMap.put("enCurso", "false");


                reference.child(idP).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                    //    progressDialog.dismiss();
                        dismiss();
                        Navigation.findNavController(getActivity(), R.id.fragment_navigation).navigate(
                                R.id.action_dialogConfirmaEntrega_to_principalRepartidor);
                        CustomToast.showOkToastRepartidor(getActivity(), "Pedido entregado");
                    }
                });
                break;
            case R.id.buttonCancelar:
                dismiss();
                break;
        }
    }
}
