package com.recordatoriodemedicamentos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    Button btnAgregar;
    Button btnModificar;
    private Medicamento medicamento;
    String idMedMod;

    SharedPreferences preMed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamento);

        preMed = getApplicationContext().getSharedPreferences("typeBoton",MODE_PRIVATE);

        btnAgregar = (Button) findViewById(R.id.btnAgregar);
        btnModificar = (Button) findViewById(R.id.btnModificar);

        authProvider = new AuthProvider();
        mediProvider = new MedicamentoProvider();

        nombre = (EditText) findViewById(R.id.edtNomMedicamento);
        unidad = (EditText) findViewById(R.id.edtUnidad);
        duracion = (EditText) findViewById(R.id.edtDuracion);
        recordar = (EditText) findViewById(R.id.edtRecordar);
        priToma = (EditText) findViewById(R.id.edtPriToma);

        String typeUser = preMed.getString("boton", "");
        if(typeUser.equals("agregar")) {
            //MyToolbar.show(this, "Añadir medicamento", true);
            btnAgregar.setVisibility(View.VISIBLE);
        }else if (typeUser.equals("modificar")){
            //
            btnModificar.setVisibility(View.VISIBLE);
            medicamento = (Medicamento) getIntent().getSerializableExtra("medicamento");
            obtenerDatos();
        }
    }

    private void obtenerDatos() {
        idMedMod = String.valueOf(medicamento.getId());
        nombre.setText(medicamento.getNombre());
        unidad.setText(medicamento.getUnidad());
        duracion.setText(medicamento.getDuracion());
        recordar.setText(medicamento.getRecordar());
        priToma.setText(medicamento.getPriToma());
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnAgregar:
                AgregarMedicamento();
                break;
            case R.id.btnModificar:
                modificarMedicamento();
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
            String idMedAgr = UUID.randomUUID().toString();
            Medicamento mAgr = new Medicamento(idMedAgr,Nombre,Unidad,Duracion,Recordar,PriToma);
            mediProvider.createMedicamento(authProvider.getId(),mAgr);
            finish();
        } else {
            Toast.makeText(this, "Ingrese todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void modificarMedicamento(){
        final String Nombre = nombre.getText().toString();
        final String  Unidad = unidad.getText().toString();
        final String Duracion = duracion.getText().toString();
        final String Recordar = recordar.getText().toString();
        final String PriToma = priToma.getText().toString();

        if(!Nombre.isEmpty() && !Unidad.isEmpty() && !Duracion.isEmpty() && !Recordar.isEmpty() && !PriToma.isEmpty()){
            Medicamento mMod = new Medicamento(idMedMod,Nombre,Unidad,Duracion,Recordar,PriToma);
            mediProvider.changeMedicamento(authProvider.getId(),mMod);
            finish();
        } else {
            Toast.makeText(this, "Ingrese todos los campos", Toast.LENGTH_SHORT).show();
        }

    }
}