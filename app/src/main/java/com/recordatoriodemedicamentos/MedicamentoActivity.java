package com.recordatoriodemedicamentos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.recordatoriodemedicamentos.Modelo.AuthProvider;
import com.recordatoriodemedicamentos.Modelo.Medicamento;
import com.recordatoriodemedicamentos.Modelo.MedicamentoProvider;

import java.util.UUID;

public class MedicamentoActivity extends AppCompatActivity {
    EditText nombre,unidad,duracion,recordar,priToma;
    AuthProvider authProvider;
    MedicamentoProvider mediProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamento);
        MyToolbar.show(this, "Añadir medicamento", true);

        authProvider = new AuthProvider();
        mediProvider = new MedicamentoProvider();

        nombre = (EditText) findViewById(R.id.edtNomMedicamento);
        unidad = (EditText) findViewById(R.id.edtUnidad);
        duracion = (EditText) findViewById(R.id.edtDuracion);
        recordar = (EditText) findViewById(R.id.edtRecordar);
        priToma = (EditText) findViewById(R.id.edtPriToma);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnAgregar:
                AgregarMedicamento();
                break;
        }
    }

    public void AgregarMedicamento(){
        final String Nombre = nombre.getText().toString();
        final String  Unidad = unidad.getText().toString();
        final String Duracion = duracion.getText().toString();
        final String Recordar = recordar.getText().toString();
        final String PriToma = priToma.getText().toString();

        if(!Nombre.isEmpty() && !Unidad.isEmpty() && !Duracion.isEmpty() && !Recordar.isEmpty() && !PriToma.isEmpty()){
            String idMedicamento = UUID.randomUUID().toString();
            Medicamento medicamento = new Medicamento(idMedicamento,Nombre,Unidad,Duracion,Recordar,PriToma);
            mediProvider.createMedicamento(authProvider.getId(),medicamento);
            Intent intent = new Intent(MedicamentoActivity.this, PrincipalActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Ingrese todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
}