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

public class PedidoAdapterClic extends RecyclerView.Adapter<PedidoAdapterClic.PedidoHolder> implements View.OnClickListener{
    List<Pedido> lista;
    int layout;
    Activity activity;

    private View.OnClickListener listener;

    public PedidoAdapterClic(List<Pedido> lista, int layout, Activity activity) {
        this.lista = lista;
        this.layout = layout;
        this.activity = activity;
    }

    @NonNull
    @Override
    public PedidoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        view.setOnClickListener(this);
        return new PedidoHolder(view);
    }

    @Override
    public void onClick(View view) {
        if(listener!=null) {
            listener.onClick(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull PedidoAdapterClic.PedidoHolder holder, int position) {
        Pedido pedido = lista.get(position);
        holder.txtid.setText(pedido.getId());
        holder.txtfecha.setText(pedido.getFechaPedido());
        holder.txtestado.setText(pedido.getEstadoPedido());

        Glide.with(activity).load(pedido.getUrl()).into(holder.imagen);

    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class PedidoHolder extends RecyclerView.ViewHolder{
        TextView txtid, txtestado, txtfecha;
        ImageView imagen;


        public PedidoHolder(@NonNull View itemView) {
            super(itemView);
            txtid =itemView.findViewById(R.id.textView8);
            txtestado =itemView.findViewById(R.id.textView13);
            txtfecha =itemView.findViewById(R.id.textView9);
            imagen = itemView.findViewById(R.id.imageView_fotoPedido);
        }
    }
}

