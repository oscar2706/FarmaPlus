package com.example.farmaplus.pharmacy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.farmaplus.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomDialogConfirmaMedicamento extends BottomSheetDialogFragment {
    public BottomDialogConfirmaMedicamento() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_dialog_confirma_medicamento, container, false);
        return view;
    }
}

