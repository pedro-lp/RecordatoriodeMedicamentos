package com.recordatoriodemedicamentos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.recordatoriodemedicamentos.Modelo.AuthProvider;
import com.recordatoriodemedicamentos.Modelo.IPaciente;
import com.recordatoriodemedicamentos.Modelo.Medicamento;
import com.recordatoriodemedicamentos.Modelo.MedicamentoProvider;
import com.recordatoriodemedicamentos.Modelo.Paciente;
import com.recordatoriodemedicamentos.Modelo.PacienteProvider;

import java.util.UUID;

public class PacienteActivity extends AppCompatActivity {
    EditText nombre, edad;
    AuthProvider authProvider;
    PacienteProvider pacienteProvider;
    Button btnAgregar;
    private Paciente paciente;
    String idPacMod;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente);

        btnAgregar = (Button) findViewById(R.id.btnAgregarPac);

        authProvider = new AuthProvider();
        pacienteProvider = new PacienteProvider();

        nombre = (EditText) findViewById(R.id.textNombrePaciente);
        edad = (EditText) findViewById(R.id.textEdad);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarPaciente();
            }
        });
    }
    public void agregarPaciente() {
        String name = nombre.getText().toString();
        String age = edad.getText().toString();

        if (!name.isEmpty() && !age.isEmpty()) {
            String idPacAgregar = UUID.randomUUID().toString();
            Paciente pAgr = new Paciente(idPacAgregar, name, age);
            pacienteProvider.createPaciente(authProvider.getId(), pAgr);
            finish();
        } else {
            Toast.makeText(this, "Ingrese todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
    public void modificarPaciente() {
        final String Nombre = nombre.getText().toString();
        final String Edad = edad.getText().toString();

        if (!Nombre.isEmpty() && !Edad.isEmpty()) {
            Paciente pMod = new Paciente(idPacMod, Nombre, Edad);
            pacienteProvider.changePaciente(authProvider.getId(), pMod);
            finish();
        } else {
            Toast.makeText(this, "Ingrese todos los campos", Toast.LENGTH_SHORT).show();
        }

    }
}