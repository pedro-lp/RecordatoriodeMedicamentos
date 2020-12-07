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
        map.put("nombre",medicamento.getNombre() );
        map.put("dosis", medicamento.getDosis());
        return mDatabase.child(idUsuario).child(medicamento.getId()).setValue(map);
    }


}
