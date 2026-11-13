package com.example.webapi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Inicio extends AppCompatActivity {

    EditText txtCorreo, txtContrasena;

    Button btnRegistrar;
    Button btnIniciarSesion;
    Button btnCerrarSesion;

    FirebaseAuth auth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        db = FirebaseFirestore.getInstance();

        txtCorreo = findViewById(R.id.txtCorreo);
        txtContrasena = findViewById(R.id.txtContrasena);

        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        auth = FirebaseAuth.getInstance();

        btnRegistrar.setOnClickListener(v -> registrarUsuario());

        btnIniciarSesion.setOnClickListener(v -> iniciarSesion());

        btnCerrarSesion.setOnClickListener(v -> cerrarSesion());
    }

    private void registrarUsuario() {

        String correo = txtCorreo.getText().toString().trim();
        String contrasena = txtContrasena.getText().toString();

        if (correo.isEmpty() || contrasena.isEmpty()) {
            Toast.makeText(this, "Completá todos los campos",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(correo, contrasena)
                .addOnCompleteListener(this, task -> {

                    if (task.isSuccessful()) {

                        String uid = auth.getCurrentUser().getUid();

                        String email = auth.getCurrentUser().getEmail();

                        Map<String, Object> usuario = new HashMap<>();

                        usuario.put("email", email);

                        usuario.put("nombre", "Usuario APIBooks");

                        db.collection("usuarios")
                                .document(uid)
                                    .set(usuario)
                                .addOnSuccessListener(unused -> {

                                    Toast.makeText(this,
                                            "Usuario guardado en Firestore",
                                            Toast.LENGTH_SHORT).show();

                                })
                                .addOnFailureListener(e -> {

                                    Toast.makeText(this,
                                            "Error: " + e.getMessage(),
                                            Toast.LENGTH_LONG).show();

                                });
                    }
                    else {

                        Toast.makeText(this,
                                "Error: " + task.getException().getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void iniciarSesion() {

        String correo = txtCorreo.getText().toString().trim();
        String contrasena = txtContrasena.getText().toString();

        if (correo.isEmpty() || contrasena.isEmpty()) {
            Toast.makeText(this, "Completá todos los campos",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(correo, contrasena)
                .addOnCompleteListener(this, task -> {

                    if (task.isSuccessful()) {

                        String uid = auth.getCurrentUser().getUid();
                        String email = auth.getCurrentUser().getEmail();

                        Map<String, Object> usuario = new HashMap<>();

                        usuario.put("email", email);
                        usuario.put("nombre", "Usuario APIBooks");

                        db.collection("usuarios")
                                .document(uid)
                                .set(usuario)
                                .addOnSuccessListener(unused -> {
                                    Toast.makeText(this,
                                            "Usuario guardado en Firestore",
                                            Toast.LENGTH_SHORT).show();
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(this,
                                            "Error: " + e.getMessage(),
                                            Toast.LENGTH_LONG).show();
                                });

                        Toast.makeText(this,
                                "Bienvenido. UID: " + uid,
                                Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                    } else {

                        Toast.makeText(this,
                                "Error al iniciar sesión",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void cerrarSesion() {

        auth.signOut();

        Toast.makeText(this,
                "Sesión cerrada",
                Toast.LENGTH_SHORT).show();
    }
}
