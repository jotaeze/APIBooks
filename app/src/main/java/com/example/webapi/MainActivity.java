
package com.example.webapi;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText txtBuscar;
    Button btnBuscar;
    RecyclerView recyclerLibros;

    ArrayList<Libro> listaLibros;
    LibroAdapter adapter;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtBuscar = findViewById(R.id.txtBuscar);
        btnBuscar = findViewById(R.id.btnBuscar);
        recyclerLibros = findViewById(R.id.recyclerLibros);

        listaLibros = new ArrayList<>();
        adapter = new LibroAdapter(listaLibros);

        recyclerLibros.setLayoutManager(
                new LinearLayoutManager(this)
        );
        recyclerLibros.setAdapter(adapter);

        queue = Volley.newRequestQueue(this);

        btnBuscar.setOnClickListener(v -> {
            String titulo = txtBuscar.getText()
                    .toString().trim();

            if (titulo.isEmpty()) {
                Toast.makeText(
                        this,
                        "Ingresá el título de un libro",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            buscarLibros(titulo);
        });
    }


    private void buscarLibros(String titulo) {

        String url = Uri.parse(
                        "https://openlibrary.org/search.json"
                )
                .buildUpon()
                .appendQueryParameter("title", titulo)
                .appendQueryParameter(
                        "fields",
                        "title,author_name,publisher"
                )
                .appendQueryParameter("limit", "20")
                .build()
                .toString();

        listaLibros.clear();
        adapter.notifyDataSetChanged();

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,

                response -> {
                    try {
                        JSONArray resultados =
                                response.optJSONArray("docs");

                        if (resultados == null ||
                                resultados.length() == 0) {
                            Toast.makeText(
                                    this,
                                    "No se encontraron libros",
                                    Toast.LENGTH_SHORT
                            ).show();
                            return;
                        }

                        for (int i = 0;
                             i < resultados.length(); i++) {

                            JSONObject item =
                                    resultados.getJSONObject(i);

                            String nombre = item.optString(
                                    "title",
                                    "Sin título"
                            );

                            String autores = "Sin autores";
                            JSONArray autoresJSON =
                                    item.optJSONArray("author_name");

                            if (autoresJSON != null &&
                                    autoresJSON.length() > 0) {

                                ArrayList<String> nombresAutores =
                                        new ArrayList<>();

                                for (int j = 0;
                                     j < autoresJSON.length(); j++) {
                                    nombresAutores.add(
                                            autoresJSON.optString(j)
                                    );
                                }

                                autores = android.text.TextUtils.join(
                                        ", ",
                                        nombresAutores
                                );
                            }

                            String editorial =
                                    "Editorial desconocida";

                            JSONArray editorialesJSON =
                                    item.optJSONArray("publisher");

                            if (editorialesJSON != null &&
                                    editorialesJSON.length() > 0) {
                                editorial =
                                        editorialesJSON.optString(
                                                0,
                                                "Editorial desconocida"
                                        );
                            }

                            listaLibros.add(
                                    new Libro(
                                            nombre,
                                            autores,
                                            editorial
                                    )
                            );
                        }

                        adapter.notifyDataSetChanged();

                    } catch (Exception e) {
                        android.util.Log.e(
                                "OPEN_LIBRARY",
                                "Error al procesar libros",
                                e
                        );

                        Toast.makeText(
                                this,
                                "Error al procesar los libros",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                },

                error -> {
                    String detalle = "Error de conexión";

                    if (error.networkResponse != null) {
                        detalle = "HTTP " +
                                error.networkResponse.statusCode;
                    }

                    android.util.Log.e(
                            "OPEN_LIBRARY",
                            "Error al consultar Open Library",
                            error
                    );

                    Toast.makeText(
                            this,
                            detalle,
                            Toast.LENGTH_LONG
                    ).show();
                }
        );

        queue.add(request);
    }

}
