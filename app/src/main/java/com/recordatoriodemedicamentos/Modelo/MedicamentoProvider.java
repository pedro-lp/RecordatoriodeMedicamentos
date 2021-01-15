package com.recordatoriodemedicamentos.Modelo;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MedicamentoProvider {

    DatabaseReference mDatabase;

    public MedicamentoProvider() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Medicamento");
    }

    public Task<Void> createMedicamento(String idUsuario, Medicamento medicamento) {
        Map<String, Object> map = new HashMap<>();
        map.put("Nombre",medicamento.getNombre());
        map.put("Unidad", medicamento.getUnidad());
        map.put("Duración", medicamento.getDuracion());
        map.put("Recordar Cada", medicamento.getRecordar());
        map.put("Primera Toma", medicamento.getPriToma());
        return mDatabase.child(idUsuario).child(medicamento.getId()).setValue(map);
    }

    public Task<Void> changeMedicamento(String idUsuario, Medicamento medicamento) {
        Map<String, Object> map = new HashMap<>();
        map.put("Nombre",medicamento.getNombre());
        map.put("Unidad", medicamento.getUnidad());
        map.put("Duración", medicamento.getDuracion());
        map.put("Recordar Cada", medicamento.getRecordar());
        map.put("Primera Toma", medicamento.getPriToma());
        return mDatabase.child(idUsuario).child(medicamento.getId()).setValue(map);
    }

    public void removeMedicamento(String idUsuario,Medicamento medicamento,MedicamentoAdapter medicamentoAdapter) {
        String idMedicamento = String.valueOf(medicamento.getId());
        mDatabase.child(idUsuario).child(idMedicamento).removeValue();
        medicamentoAdapter .eliminarMedicamento(medicamento);
    }


    public void showMedicamento(String idUsuario, MedicamentoAdapter medicamentoAdapter, ArrayList arrayList){
        mDatabase.child(idUsuario).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    arrayList.clear();
                    Medicamento medicamento = null;
                    for(DataSnapshot ds: snapshot.getChildren()){
                        medicamento = new Medicamento();
                        medicamento.setId(ds.getKey());
                        medicamento.setNombre(ds.child("Nombre").getValue().toString());
                        medicamento.setUnidad(ds.child("Unidad").getValue().toString());
                        medicamento.setDuracion(ds.child("Duración").getValue().toString());
                        medicamento.setRecordar(ds.child("Recordar Cada").getValue().toString());
                        medicamento.setPriToma(ds.child("Primera Toma").getValue().toString());
                        medicamentoAdapter.agregarMedicamento(medicamento);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
