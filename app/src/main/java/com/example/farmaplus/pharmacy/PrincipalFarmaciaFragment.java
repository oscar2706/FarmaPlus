package com.example.farmaplus.pharmacy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.farmaplus.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PrincipalFarmaciaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrincipalFarmaciaFragment extends Fragment {

    public PrincipalFarmaciaFragment() {
        // Required empty public constructor
    }

    public static PrincipalFarmaciaFragment newInstance(String param1, String param2) {
        PrincipalFarmaciaFragment fragment = new PrincipalFarmaciaFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.farmacia_principal_farmacia, container, false);
    }
}