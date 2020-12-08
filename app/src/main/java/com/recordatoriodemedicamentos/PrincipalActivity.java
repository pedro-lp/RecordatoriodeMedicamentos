package com.recordatoriodemedicamentos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.mbms.MbmsErrors;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.recordatoriodemedicamentos.Modelo.AuthProvider;
import com.recordatoriodemedicamentos.Modelo.IMedicamento;
import com.recordatoriodemedicamentos.Modelo.Medicamento;
import com.recordatoriodemedicamentos.Modelo.MedicamentoAdapter;
import com.recordatoriodemedicamentos.Modelo.MedicamentoProvider;

import java.util.ArrayList;
import java.util.List;

public class PrincipalActivity extends AppCompatActivity implements IMedicamento {
    AuthProvider authProvider;
    MedicamentoProvider mediProvider;
    ArrayList<Medicamento> medicamentoArrayList;
    RecyclerView idrecyclerView;
    private MedicamentoAdapter medicamentoAdapter;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        MyToolbar.show(this, "Usuario", false);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        authProvider = new AuthProvider();
        mediProvider = new MedicamentoProvider();

        idrecyclerView = (RecyclerView) findViewById(R.id.rcvlistaMedicamentos);
        medicamentoArrayList = new ArrayList<>();
        medicamentoAdapter = new MedicamentoAdapter(this, medicamentoArrayList);
        idrecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        idrecyclerView.setAdapter(medicamentoAdapter);
        Mostrar();
    }

    private void Mostrar(){
        mDatabase.child("Medicamento").child(authProvider.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Medicamento medicamento = null;
                    for(DataSnapshot ds: snapshot.getChildren()){
                        medicamento = new Medicamento();
                        medicamento.setId(ds.getKey());
                        medicamento.setNombre();
                        medicamento.setUnidad();
                        medicamento.setDuracion();
                        medicamento.setRecordar();
                        medicamento.setPriToma();
                        medicamentoAdapter.agregarMedicamento(medicamento);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
