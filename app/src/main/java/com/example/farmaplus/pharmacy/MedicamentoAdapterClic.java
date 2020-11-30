package com.example.farmaplus.pharmacy;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmaplus.R;
import com.example.farmaplus.client.Sucursal;

import java.util.List;


public class MedicamentoAdapterClic extends RecyclerView.Adapter<MedicamentoAdapterClic.MedicamentoHolder> implements View.OnClickListener{
    List<Medicamento> lista;
    int layout;
    Activity activity;
    int row_index = -1;

    private View.OnClickListener listener;

    public MedicamentoAdapterClic(List<Medicamento> lista, int layout, Activity activity) {
        this.lista = lista;
        this.layout = layout;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MedicamentoAdapterClic.MedicamentoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        view.setOnClickListener(this);
        return new MedicamentoAdapterClic.MedicamentoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicamentoAdapterClic.MedicamentoHolder holder, int position) {
        Medicamento medicamento= lista.get(position);
        holder.txtnom.setText(medicamento.getMarca());
        holder.txtprecio.setText("$ "+medicamento.getPrecio());
        switch (medicamento.getFormaFarmaceutica())
        {
            case "Jarabe":
                holder.img.setImageResource(R.drawable.jarabe_emulsion);
                break;
            case "Suspensión":
                holder.img.setImageResource(R.drawable.suspension);
                break;
            case "Emulsión":
                holder.img.setImageResource(R.drawable.jarabe_emulsion);
                break;
            case "Gotas":
                holder.img.setImageResource(R.drawable.gotas);
                break;
            case "Bebible":
                holder.img.setImageResource(R.drawable.bebible);
                break;
            case "Inyectable":
                holder.img.setImageResource(R.drawable.inyectable);
                break;
            case "Comprimidos":
                holder.img.setImageResource(R.drawable.comprimidos);
                break;
            case "Grageas":
                holder.img.setImageResource(R.drawable.grageas);
                break;
            case "Efervescentes":
                holder.img.setImageResource(R.drawable.efervescentes);
                break;
            case "Cápsulas":
                holder.img.setImageResource(R.drawable.capsulas);
                break;
            case "Tabletas":
                holder.img.setImageResource(R.drawable.tabletas);
                break;
            case "Pomada":
                holder.img.setImageResource(R.drawable.pomada_crema);
                break;
            case "Gel":
                holder.img.setImageResource(R.drawable.gel);
                break;
            case "Crema":
                holder.img.setImageResource(R.drawable.pomada_crema);
                break;
        }


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

    public class MedicamentoHolder extends RecyclerView.ViewHolder{
        TextView txtprecio, txtnom;
        ImageView img;


        public MedicamentoHolder(@NonNull View itemView) {
            super(itemView);
            txtprecio =itemView.findViewById(R.id.txt_precio);
            txtnom =itemView.findViewById(R.id.txt_nombreMedicamento);
            img =itemView.findViewById(R.id.imageView_medicamento);


        }
    }
}

