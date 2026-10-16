
package com.example.webapi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LibroAdapter
        extends RecyclerView.Adapter<LibroAdapter.LibroViewHolder> {

    private ArrayList<Libro> listaLibros;

    public LibroAdapter(ArrayList<Libro> listaLibros) {
        this.listaLibros = listaLibros;
    }

    @NonNull
    @Override
    public LibroViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_libro, parent, false);

        return new LibroViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(
            @NonNull LibroViewHolder holder, int position) {

        Libro libro = listaLibros.get(position);

        holder.txtTitulo.setText(libro.getTitulo());
        holder.txtAutores.setText(libro.getAutores());
        holder.txtEditorial.setText(libro.getEditorial());
    }

    @Override
    public int getItemCount() {
        return listaLibros.size();
    }

    static class LibroViewHolder
            extends RecyclerView.ViewHolder {

        TextView txtTitulo;
        TextView txtAutores;
        TextView txtEditorial;

        public LibroViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitulo = itemView.findViewById(R.id.txtTitulo);
            txtAutores = itemView.findViewById(R.id.txtAutores);
            txtEditorial = itemView.findViewById(R.id.txtEditorial);
        }
    }
}
