package com.recordatoriodemedicamentos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText correo, contra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //correo = findViewById(R.id.txtUsuario);
        //contra = findViewById(R.id.txtContra);
    }
}