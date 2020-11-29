package com.example.farmaplus.pharmacy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.farmaplus.R;
import com.github.chrisbanes.photoview.PhotoView;

public class recetaDetalleImagen extends Fragment {
    String url;

    public recetaDetalleImagen() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_receta_detalle_imagen, container, false);

        try {
            PhotoView photoView = (PhotoView) view.findViewById(R.id.photo_view);
            url = getArguments().getString("url");
            Glide.with(getActivity()).load(url).into(photoView);
        }catch (Exception e){
        }

        return view;
    }
}