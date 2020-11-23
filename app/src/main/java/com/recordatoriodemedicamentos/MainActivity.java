package com.recordatoriodemedicamentos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
    public void onClick(View v){
        switch(v.getId()){
            case R.id.btnSalir:
                System.exit(0);
                break;
            case R.id.btnRegistrar:
                startActivity(new Intent(MainActivity.this, Registrar.class));
                break;
        }
    }
}