package com.example.farmaplus.client;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmaplus.R;

import java.util.List;

public class DireccionAdapterClic extends RecyclerView.Adapter<DireccionAdapterClic.DireccionHolder> implements View.OnClickListener{
    List<Direccion> lista;
    int layout;
    Activity activity;

    private View.OnClickListener listener;

    public DireccionAdapterClic(List<Direccion> lista, int layout, Activity activity) {
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
    public void onBindViewHolder(@NonNull DireccionHolder holder, int position) {
        Direccion direccion= lista.get(position);
        holder.txtnom.setText(direccion.getNombreDireccion());
        holder.txtdirec.setText(direccion.getDireccion());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener!=null) {
            listener.onClick(view);
        }
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
