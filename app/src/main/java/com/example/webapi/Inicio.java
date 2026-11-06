package com.example.webapi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Inicio extends AppCompatActivity {

    EditText txtCorreo, txtContrasena;

    Button btnRegistrar;
    Button btnIniciarSesion;
    Button btnCerrarSesion;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

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

                        Toast.makeText(this,
                                "Usuario registrado correctamente",
                                Toast.LENGTH_SHORT).show();

                    } else {

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
