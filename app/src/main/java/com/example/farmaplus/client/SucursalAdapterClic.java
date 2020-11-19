package com.example.farmaplus.client;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmaplus.R;

import java.util.List;


public class SucursalAdapterClic extends RecyclerView.Adapter<SucursalAdapterClic.SucursalHolder> implements View.OnClickListener{
    List<Sucursal> lista;
    int layout;
    Activity activity;
    int row_index = -1;

    private View.OnClickListener listener;

    public SucursalAdapterClic(List<Sucursal> lista, int layout, Activity activity) {
        this.lista = lista;
        this.layout = layout;
        this.activity = activity;
    }

    @NonNull
    @Override
    public SucursalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        view.setOnClickListener(this);
        return new SucursalHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SucursalHolder holder, int position) {
        Sucursal sucursal= lista.get(position);
        holder.txtnom.setText(sucursal.getNombreSuc());
        holder.txtdirec.setText(sucursal.getDireccionSuc());


      /*  holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index=position;
                notifyDataSetChanged();
            }
        });

        if(row_index==position){
            holder.card.setBackgroundColor(Color.parseColor("#567845"));
          //  holder.tv1.setTextColor(Color.parseColor("#ffffff"));
        }
        else
        {
            holder.card.setBackgroundColor(Color.parseColor("#ffffff"));
          //  holder.tv1.setTextColor(Color.parseColor("#000000"));
        } */
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

    public class SucursalHolder extends RecyclerView.ViewHolder{
        TextView txtdirec, txtnom;
        CardView card;

        public SucursalHolder(@NonNull View itemView) {
            super(itemView);
            txtdirec =itemView.findViewById(R.id.textView16);
            txtnom =itemView.findViewById(R.id.textView15);
            card = itemView.findViewById(R.id.card_suc);

        }
    }
}
