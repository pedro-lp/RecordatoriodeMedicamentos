package com.recordatoriodemedicamentos.Modelo;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MedicamentoProvider {

    DatabaseReference mDatabase;

    public MedicamentoProvider() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Medicamento");
    }

    public Task<Void> createMedicina(String idUsuario,Medicamento medicamento) {
        Map<String, Object> map = new HashMap<>();
        map.put("Nombre",medicamento.getNombre());
        map.put("Unidad", medicamento.getUnidad());
        map.put("Duración", medicamento.getDuracion());
        map.put("Recordar Cada", medicamento.getRecordar());
        map.put("Primera Toma", medicamento.getPriToma());
        return mDatabase.child(idUsuario).child(medicamento.getId()).setValue(map);
    }

    public void showMedicamentos(String idUsuario, MedicamentoAdapter medicamentoAdapter) {
        mDatabase.child("Medicamento").child(idUsuario).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot objSnaptshot : snapshot.getChildren()){
                    Medicamento medicamento = objSnaptshot.getValue(Medicamento.class);
                    medicamentoAdapter.agregarMedicamento(medicamento);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
