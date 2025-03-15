package com.example.cumn_rv;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class PalaAdapter extends RecyclerView.Adapter<PalaAdapter.PalaViewHolder> {
    private List<Pala> listaPalas;
    private Context context;

    public PalaAdapter(Context context, List<Pala> palas) {
        this.context = context;
        this.listaPalas = palas;
    }

    @NonNull
    @Override
    public PalaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pala, parent, false);
        return new PalaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PalaViewHolder holder, int position) {
        Pala pala = listaPalas.get(position);
        holder.nombrePala.setText(pala.getNombre());
        holder.descripcionPala.setText(pala.getDescripcion());

        Glide.with(context)
                .load(pala.getImagenUrl())
                .placeholder(R.drawable.placeholder_pala)
                .error(R.drawable.error_pala)
                .into(holder.imagenPala);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetallePala.class);
            intent.putExtra("PALA_NOMBRE", pala.getNombre());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listaPalas.size();
    }

    public static class PalaViewHolder extends RecyclerView.ViewHolder {
        TextView nombrePala, descripcionPala;
        ImageView imagenPala;

        public PalaViewHolder(@NonNull View itemView) {
            super(itemView);
            nombrePala = itemView.findViewById(R.id.nombrePala);
            descripcionPala = itemView.findViewById(R.id.descripcionPala);
            imagenPala = itemView.findViewById(R.id.imagenPala);
        }
    }
}
