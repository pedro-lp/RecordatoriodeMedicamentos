package com.recordatoriodemedicamentos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.recordatoriodemedicamentos.Notificacion.CreateNotification;

public class MainActivity extends AppCompatActivity {
    Button btnIngresar;
    Button btnIniciarSesion;
    Button btnRestrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRestrarse = findViewById(R.id.btnRegistrate);
        btnIngresar = findViewById(R.id.btnIngresar);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);

        btnRestrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registrate();
            }
        });

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ingresar();
            }
        });

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IniciarSesion();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {

            Intent intent = new Intent(MainActivity.this, PrincipalActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void Ingresar() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void Registrate() {
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    private void IniciarSesion() {
        Intent intent = new Intent(MainActivity.this, SelectUserActivity.class);
        startActivity(intent);
    }
}