package com.recordatoriodemedicamentos.Modelo;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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


}
