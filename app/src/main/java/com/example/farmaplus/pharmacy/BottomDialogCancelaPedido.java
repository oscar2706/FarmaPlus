package com.example.farmaplus.pharmacy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.farmaplus.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomDialogCancelaPedido extends BottomSheetDialogFragment {
    public BottomDialogCancelaPedido() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_dialog_farmacia_cancela_pedido, container, false);
        return view;
    }
}
