package com.recordatoriodemedicamentos.modelo;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class UsuarioControlador {
    DatabaseReference mDatabase;

    public UsuarioControlador() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Clients");
    }

    public Task<Void> create(Usuario usuario) {
        Map<String, Object> map = new HashMap<>();
        map.put("nombre", usuario.getNombre());
        map.put("correo", usuario.getCorreo());
        return mDatabase.child(usuario.getId()).setValue(map);
    }
}
