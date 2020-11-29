package com.example.farmaplus.pharmacy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmaplus.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class BottomDialogCancelaPedido extends BottomSheetDialogFragment implements View.OnClickListener {
    Button buttonAceptar, buttonCancelar;
    TextView txt_com;
    String idP;

    public BottomDialogCancelaPedido() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_dialog_farmacia_cancela_pedido, container, false);
        buttonAceptar = view.findViewById(R.id.buttonAceptar);
        buttonCancelar = view.findViewById(R.id.buttonCancelar);
        buttonAceptar.setOnClickListener(this);
        buttonCancelar.setOnClickListener(this);
        txt_com = view.findViewById(R.id.edittxt_com);

        try {
            idP = getArguments().getString("idP");
        }catch (Exception e){

        }

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonAceptar:
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference("PEDIDO");

                HashMap hashMap = new HashMap();
                hashMap.put("estadoPedido", "Cancelado");
                hashMap.put("comentarios", txt_com.getText().toString());
                hashMap.put("enCurso", "false");


                reference.child(idP).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        //    progressDialog.dismiss();
                        Toast.makeText(getContext(), "PEDIDO CANCELADO", Toast.LENGTH_LONG).show();
                        Navigation.findNavController(getActivity(), R.id.fragment_navigation).navigate(
                                R.id.action_bottomDialogCancelaPedido_to_principalFarmacia);
                    }
                });
                break;
            case R.id.buttonCancelar:
                dismiss();
                break;
        }

    }
}
