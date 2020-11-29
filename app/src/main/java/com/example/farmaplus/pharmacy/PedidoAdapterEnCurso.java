package com.example.farmaplus.pharmacy;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmaplus.R;
import com.example.farmaplus.client.Pedido;

import java.util.List;


public class PedidoAdapterEnCurso extends RecyclerView.Adapter<PedidoAdapterEnCurso.PedidoHolder> implements View.OnClickListener {
    List<Pedido> lista;
    int layout;
    Activity activity;

    private View.OnClickListener listener;

    public PedidoAdapterEnCurso(List<Pedido> lista, int layout, Activity activity) {
        this.lista = lista;
        this.layout = layout;
        this.activity = activity;
    }

    @NonNull
    @Override
    public PedidoAdapterEnCurso.PedidoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        view.setOnClickListener(this);
        return new PedidoAdapterEnCurso.PedidoHolder(view);
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull PedidoAdapterEnCurso.PedidoHolder holder, int position) {
        Pedido pedido = lista.get(position);

        holder.txtid.setText(pedido.getId());
        if (pedido.getTipoEntrega().equals("Domicilio")){
            String string = pedido.getRepartidor();
            String[] parts = string.split(" ");
            String nombre = parts[0]+" "+parts[1];
            holder.txtrep.setText(nombre);
        }else if(pedido.getTipoEntrega().equals("En Sucursal")){
            holder.txtrep.setText(pedido.getLugarEntrega());
        }
        holder.txtEstado.setText(pedido.getEstadoPedido());
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class PedidoHolder extends RecyclerView.ViewHolder {
        TextView txtid, txtrep, txtEstado;


        public PedidoHolder(@NonNull View itemView) {
            super(itemView);
            txtid = itemView.findViewById(R.id.textView_idPedido);
            txtrep = itemView.findViewById(R.id.textView_repartidor);
            txtEstado = itemView.findViewById(R.id.textView_estadoPedido);
        }
    }
}
