package com.recordatoriodemedicamentos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PerfilUsuario extends AppCompatActivity {
    Button btnCerrarSesion, btnSalirPerfil;
    TextView usuario;
    FirebaseAuth auth;
    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);
        btnCerrarSesion = (Button) findViewById(R.id.btnCerrarSesion);
        btnSalirPerfil = (Button) findViewById(R.id.btnSalirPerfil);
        usuario = (TextView) findViewById(R.id.lblUsuario);
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();
        informacion();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSalirPerfil:
                //startActivity(new Intent(PerfilUsuario.this, MainActivity.class));
                Intent i = new Intent(Intent.ACTION_MAIN);
                i.addCategory(Intent.CATEGORY_HOME);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                break;
            case R.id.btnCerrarSesion:
                auth.signOut();
                startActivity(new Intent(PerfilUsuario.this, InicioSesion.class));
                finish();
                break;
        }
    }

    private void informacion() {
        String id = auth.getCurrentUser().getUid();
        db.child("usuarios").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String nombre = snapshot.child("nombre").getValue().toString();
                    usuario.setText(nombre);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
