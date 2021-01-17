package com.recordatoriodemedicamentos.Modelo;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PacienteProvider {

    DatabaseReference mDatabase;

    public PacienteProvider() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Paciente");
    }

    public Task<Void> createPaciente(String idUsuario, Paciente paciente) {
        Map<String, Object> map = new HashMap<>();
        map.put("Nombre", paciente.getNombre());
        map.put("Edad", paciente.getEdad());
        return mDatabase.child(idUsuario).child(paciente.getId()).setValue(map);
    }

    public Task<Void> changePaciente(String idUsuario, Paciente paciente) {
        Map<String, Object> map = new HashMap<>();
        map.put("Nombre", paciente.getNombre());
        map.put("Edad", paciente.getEdad());
        return mDatabase.child(idUsuario).child(paciente.getId()).setValue(map);
    }

    public void removePaciente(String idUsuario, Paciente paciente, PacienteAdapter pacienteAdapter) {
        String idPaciente = String.valueOf(paciente.getId());
        mDatabase.child(idUsuario).child(idPaciente).removeValue();
        pacienteAdapter.eliminarPaciente(paciente);
    }


    public void showPaciente(String idUsuario, PacienteAdapter pacienteAdapter, ArrayList arrayList) {
        mDatabase.child(idUsuario).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    arrayList.clear();
                    Paciente paciente = null;
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        paciente = new Paciente();
                        paciente.setId(ds.getKey());
                        paciente.setNombre(ds.child("Nombre").getValue().toString());
                        paciente.setEdad(ds.child("Edad").getValue().toString());
                        pacienteAdapter.agregarPaciente(paciente);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
