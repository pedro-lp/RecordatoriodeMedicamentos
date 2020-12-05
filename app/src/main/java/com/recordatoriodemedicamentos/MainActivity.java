package com.recordatoriodemedicamentos;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
        //fin de la alarma

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSalir:
                Intent i = new Intent(Intent.ACTION_MAIN);
                i.addCategory(Intent.CATEGORY_HOME);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                break;
            case R.id.btnEntrar:
                startActivity(new Intent(MainActivity.this, Registrar.class));
                break;
            case R.id.btnRecordatorios:
                startActivity(new Intent(MainActivity.this, Recordatorios.class));
                break;

        }
    }
}