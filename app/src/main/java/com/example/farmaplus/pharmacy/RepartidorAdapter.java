package com.example.farmaplus.pharmacy;

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
import com.example.farmaplus.Repartidor;
import com.example.farmaplus.client.Direccion;

import java.util.List;


public class RepartidorAdapter extends RecyclerView.Adapter<RepartidorAdapter.RepartidorHolder> implements View.OnClickListener{
    List<Repartidor> lista;
    int layout;
    Activity activity;

    public RepartidorAdapter(List<Repartidor> lista, int layout, Activity activity) {
        this.lista = lista;
        this.layout = layout;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RepartidorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        view.setOnClickListener(this);
        return new RepartidorHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RepartidorAdapter.RepartidorHolder holder, int position) {
        Repartidor repartidor= lista.get(position);
        holder.txtnom.setText(repartidor.getNombre());

        Glide.with(activity).load(repartidor.getFotoRepartidor()).into(holder.imagen);

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    @Override
    public void onClick(View view) {

    }

    public class RepartidorHolder extends RecyclerView.ViewHolder{
        TextView txtnom;
        ImageView imagen;

        public RepartidorHolder(@NonNull View itemView) {
            super(itemView);
            txtnom =itemView.findViewById(R.id.textView35);
            imagen = itemView.findViewById(R.id.imageView14);

        }
    }
}

