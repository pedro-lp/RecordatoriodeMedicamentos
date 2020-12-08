package com.recordatoriodemedicamentos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.recordatoriodemedicamentos.Modelo.AuthProvider;
import com.recordatoriodemedicamentos.Modelo.IMedicamento;
import com.recordatoriodemedicamentos.Modelo.Medicamento;
import com.recordatoriodemedicamentos.Modelo.MedicamentoAdapter;
import com.recordatoriodemedicamentos.Modelo.MedicamentoProvider;

import java.util.ArrayList;

public class PrincipalActivity extends AppCompatActivity implements IMedicamento {
    AuthProvider authProvider;
    MedicamentoProvider mediProvider;
    ArrayList<Medicamento> medicamentoArrayList;
    RecyclerView idrecyclerView;
    private MedicamentoAdapter medicamentoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        MyToolbar.show(this, "Usuario", false);

        authProvider = new AuthProvider();
        mediProvider = new MedicamentoProvider();

        idrecyclerView = (RecyclerView) findViewById(R.id.rcvlistaMedicamentos);
        medicamentoArrayList = new ArrayList<>();
        medicamentoAdapter = new MedicamentoAdapter(this,medicamentoArrayList);

        RecyclerView recyclerView = findViewById(R.id.rcvlistaMedicamentos);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setAdapter(medicamentoAdapter);
        mediProvider.showMedicamentos(authProvider.getId(),medicamentoAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_usuario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            authProvider.logout();
            Intent intent = new Intent(PrincipalActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        if (item.getItemId() == R.id.notificacion) {
            Intent intent = new Intent(PrincipalActivity.this, Recordatorios.class);
            startActivity(intent);
            //finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view){
        Intent miIntent = null;
        switch (view.getId()){
            case R.id.btnAgrMed:
                Intent intent = new Intent(PrincipalActivity.this, MedicamentoActivity.class);
                startActivity(intent);
                break;
        }
    }


    @Override
    public void OpcionEditar(Medicamento medicamento) {

    }

    @Override
    public void OpcionEliminar(Medicamento medicamento) {

    }
}
