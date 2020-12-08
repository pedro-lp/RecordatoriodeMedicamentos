package com.recordatoriodemedicamentos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MedicamentoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamento);
        MyToolbar.show(this, "Añadir medicamento", false);
    }
}