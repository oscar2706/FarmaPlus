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
import com.example.farmaplus.client.Pedido;

import java.util.List;

public class PedidoAdapterNuevo extends RecyclerView.Adapter<PedidoAdapterNuevo.PedidoHolder> implements View.OnClickListener{
    List<Pedido> lista;
    int layout;
    Activity activity;

    private View.OnClickListener listener;

    public PedidoAdapterNuevo(List<Pedido> lista, int layout, Activity activity) {
        this.lista = lista;
        this.layout = layout;
        this.activity = activity;
    }

    @NonNull
    @Override
    public PedidoAdapterNuevo.PedidoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        view.setOnClickListener(this);
        return new PedidoAdapterNuevo.PedidoHolder(view);
    }

    @Override
    public void onClick(View view) {
        if(listener!=null) {
            listener.onClick(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull PedidoAdapterNuevo.PedidoHolder holder, int position) {
        Pedido pedido = lista.get(position);
        holder.txtid.setText(pedido.getId());
        holder.txtfecha.setText(pedido.getFechaPedido());
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class PedidoHolder extends RecyclerView.ViewHolder{
        TextView txtid, txtfecha;


        public PedidoHolder(@NonNull View itemView) {
            super(itemView);
            txtid =itemView.findViewById(R.id.textView33);
            txtfecha =itemView.findViewById(R.id.textView34);
        }
    }
}
