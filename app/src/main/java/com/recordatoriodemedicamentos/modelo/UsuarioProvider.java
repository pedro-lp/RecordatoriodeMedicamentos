package com.recordatoriodemedicamentos.modelo;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class UsuarioProvider {

    DatabaseReference mDatabase;

    public UsuarioProvider() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Usuario");
    }

    public Task<Void> create(Usuario usuario) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", usuario.getName());
        map.put("email", usuario.getEmail());
        return mDatabase.child(usuario.getId()).setValue(map);
    }


}
