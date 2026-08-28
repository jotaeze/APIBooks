package com.example.webapi;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerPersonajes;

    ArrayList<Personaje> listaPersonajes;

    PersonajeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        recyclerPersonajes =
                findViewById(R.id.recyclerPersonajes);

        listaPersonajes = new ArrayList<>();

        adapter = new PersonajeAdapter(listaPersonajes);

        recyclerPersonajes.setLayoutManager(
                new LinearLayoutManager(this)
        );

        recyclerPersonajes.setAdapter(adapter);

        obtenerPersonajes();
    }

    private void obtenerPersonajes() {

        String url = "https://rickandmortyapi.com/api/character";

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,

                response -> {

                    try {
                        JSONArray resultados = response.getJSONArray("results");

                        for (int i = 0; i < resultados.length(); i++) {

                            JSONObject json = resultados.getJSONObject(i);

                            Personaje personaje = new Personaje(
                                    json.getString("name"),
                                    json.getString("status"),
                                    json.getString("species")
                            );

                            listaPersonajes.add(personaje);
                        }

                        adapter.notifyDataSetChanged();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },

                volleyError -> {
                    Toast.makeText(
                            this,
                            "Error al consultar la API",
                            Toast.LENGTH_SHORT
                    ).show();
                }
        );

        queue.add(request);
    }

/*    private void obtenerPersonajes() {
        new Thread(() -> {

            try {

                URL url = new URL("https://rickandmortyapi.com/api/character");

                HttpURLConnection conexion = (HttpURLConnection) url.openConnection();

                conexion.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(conexion.getInputStream()));

                StringBuilder respuesta = new StringBuilder();

                String linea;

                while ((linea = reader.readLine()) != null) {
                    respuesta.append(linea);
                }

                reader.close();

                conexion.disconnect();

                JSONObject json = new JSONObject(respuesta.toString());

                JSONArray resultados = json.getJSONArray("results");

                for (int i = 0; i < resultados.length(); i++) {

                    JSONObject personajeJSON = resultados.getJSONObject(i);

                    String nombre = personajeJSON.getString("name");

                    String estado = personajeJSON.getString("status");

                    String especie = personajeJSON.getString("species");

                    Personaje personaje = new Personaje(
                                    nombre,
                                    estado,
                                    especie
                            );

                    listaPersonajes.add(personaje);
                }

                runOnUiThread(() -> {
                    adapter.notifyDataSetChanged();
                });

            } catch (Exception e) {
                runOnUiThread(() -> {
                    Toast.makeText(
                            this,
                            "Error al consultar la API",
                            Toast.LENGTH_SHORT
                    ).show();
                });
            }

        }).start();
    }*/
}