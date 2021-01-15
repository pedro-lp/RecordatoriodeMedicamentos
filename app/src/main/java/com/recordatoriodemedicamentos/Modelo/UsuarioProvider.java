package com.recordatoriodemedicamentos.Modelo;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class UsuarioProvider {

    DatabaseReference mDatabase;

    public UsuarioProvider() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Usuarios").child("Tutores");
    }

    public Task<Void> create(Usuario usuario) {
        Map<String, Object> map = new HashMap<>();
        map.put("nombre", usuario.getName());
        map.put("correo", usuario.getEmail());
        return mDatabase.child(usuario.getId()).setValue(map);
    }
}
