package com.recordatoriodemedicamentos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Registrar extends AppCompatActivity {
    private Button btnSalir, btnInicioSesion, btnRegistrar;
    private EditText txtNombre, txtCorreo,txtContrasenia;
    private String nombre, correo, contrasenia;
    FirebaseAuth aut;
    DatabaseReference refBD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        btnSalir = (Button) findViewById(R.id.btnSalir);
        btnInicioSesion = (Button) findViewById(R.id.btnInicioSesion);
        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);

        txtNombre = (EditText) findViewById(R.id.editNombre);
        txtContrasenia = (EditText) findViewById(R.id.editContrasenia);
        txtCorreo = (EditText) findViewById(R.id.editCorreo);

        aut = FirebaseAuth.getInstance();
        refBD = FirebaseDatabase.getInstance().getReference();
    }
    public void onClick(View v){
        switch(v.getId()){
            case R.id.btnSalir:
                System.exit(0);
                break;
            case R.id.btnInicioSesion:
                break;
            case R.id.btnRegistrar:
                nombre = txtNombre.getText().toString();
                correo = txtCorreo.getText().toString();
                contrasenia = txtContrasenia.getText().toString();
                if(!nombre.isEmpty() && !correo.isEmpty() && !contrasenia.isEmpty()){
                    if(contrasenia.length() >= 6){
                        registrar();
                    }else{
                        Toast.makeText(this, "La contraseña debe tener almenos 6 caracteres", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "Debe rellenar los campos solicitados", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void registrar() {
        aut.createUserWithEmailAndPassword(correo,contrasenia).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Map<String, Object> map = new HashMap<>();
                    map.put("nombre",nombre);
                    map.put("correo",correo);
                    map.put("contrasenia",contrasenia);

                    String id = aut.getCurrentUser().getUid();
                    refBD.child("usuarios").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if(task2.isSuccessful()){
                                startActivity(new Intent(Registrar.this,InicioSesion.class));
                                finish();
                            }else{
                                Toast.makeText(Registrar.this, "Ah ocurrido un error con los datos", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(Registrar.this, "No fue posible registrar el usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}