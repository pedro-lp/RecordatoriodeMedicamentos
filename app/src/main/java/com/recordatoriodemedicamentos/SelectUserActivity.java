package com.recordatoriodemedicamentos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SelectUserActivity extends AppCompatActivity {
    Button btnTutor;
    Button btnPaciente;

    SharedPreferences mPref;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);
        getSupportActionBar().setTitle("Seleccionar tipo Usuario");
        //MyToolbar.show(this, "Seleccionar Opción", true);

        mPref = getApplicationContext().getSharedPreferences("tipoUsuario", MODE_PRIVATE);
        editor = mPref.edit();

        btnTutor = findViewById(R.id.btnTutor);
        btnPaciente = findViewById(R.id.btnPaciente);
        btnTutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
        btnPaciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });
    }

    public void Login() {
        editor.putString("usuario", "tutor");
        editor.apply();
        Intent intent = new Intent(SelectUserActivity.this, IniciarSesionActivity.class);
        startActivity(intent);
    }

    public void Register() {
        editor.putString("usuario", "paciente");
        editor.apply();
        Intent intent = new Intent(SelectUserActivity.this, IniciarSesionActivity.class);
        startActivity(intent);
    }
}