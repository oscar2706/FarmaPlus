package com.example.farmaplus.pharmacy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.farmaplus.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomDialogConfirmaNuevoRepartidor extends BottomSheetDialogFragment {
    public BottomDialogConfirmaNuevoRepartidor() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_dialog_confirma_registra_repartidor, container, false);
        return view;
    }
}
