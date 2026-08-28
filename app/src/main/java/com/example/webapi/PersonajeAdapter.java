package com.example.webapi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PersonajeAdapter extends RecyclerView.Adapter<PersonajeAdapter.ViewHolder>{

    private ArrayList<Personaje> lista;

    public PersonajeAdapter(ArrayList<Personaje> lista) {
        this.lista = lista;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtNombre;
        TextView txtInfo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtInfo = itemView.findViewById(R.id.txtInfo);
        }
    }

    @NonNull
    @Override
    public PersonajeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_personaje,
                parent,
                false
        );

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonajeAdapter.ViewHolder holder, int position) {
        Personaje personaje = lista.get(position);

        holder.txtNombre.setText(
                personaje.getNombre()
        );

        holder.txtInfo.setText(
                personaje.getEspecie()
                        + " - "
                        + personaje.getEstado()
        );
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}
