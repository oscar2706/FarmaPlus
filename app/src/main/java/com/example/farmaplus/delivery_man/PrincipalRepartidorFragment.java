package com.example.farmaplus.delivery_man;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.farmaplus.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PrincipalRepartidorFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class PrincipalRepartidorFragment extends Fragment {

    public static PrincipalRepartidorFragment newInstance(String param1, String param2) {
        PrincipalRepartidorFragment fragment = new PrincipalRepartidorFragment();
        return fragment;
    }

    public PrincipalRepartidorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.repartidor_principal, container, false);
    }
}