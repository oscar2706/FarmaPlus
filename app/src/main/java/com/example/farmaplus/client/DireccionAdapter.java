package com.example.farmaplus.client;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.farmaplus.R;

import java.util.List;

public class DireccionAdapter extends RecyclerView.Adapter<DireccionAdapter.DireccionHolder> implements View.OnClickListener{
    List<Direccion> lista;
    int layout;
    Activity activity;

    public DireccionAdapter(List<Direccion> lista, int layout, Activity activity) {
        this.lista = lista;
        this.layout = layout;
        this.activity = activity;
    }

    @NonNull
    @Override
    public DireccionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        view.setOnClickListener(this);
        return new DireccionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DireccionAdapter.DireccionHolder holder, int position) {
        Direccion direccion= lista.get(position);
        holder.txtnom.setText(direccion.getNombreDireccion());
        holder.txtdirec.setText(direccion.getDireccion());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    @Override
    public void onClick(View view) {

    }

    public class DireccionHolder extends RecyclerView.ViewHolder{
        TextView txtdirec, txtnom;

        public DireccionHolder(@NonNull View itemView) {
            super(itemView);
            txtdirec =itemView.findViewById(R.id.txt_nombreDirecItem);
            txtnom =itemView.findViewById(R.id.txt_direccionItem);

        }
    }
}
