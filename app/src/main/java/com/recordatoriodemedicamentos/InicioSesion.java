package com.recordatoriodemedicamentos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class InicioSesion extends AppCompatActivity {
    private Button btnAcceder;
    private EditText editCorreo, editContrasenia;
    private String correo = "", contrasenia = "";
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);
        editCorreo = (EditText) findViewById(R.id.editCorreoLogin);
        editContrasenia = (EditText) findViewById(R.id.editContraseniaLogin);
        btnAcceder = (Button) findViewById(R.id.btnAcceder);
        auth = FirebaseAuth.getInstance();
        
        btnAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correo = editCorreo.getText().toString().trim();
                contrasenia = editContrasenia.getText().toString().trim();
                
                if(!correo.isEmpty() && !contrasenia.isEmpty()){
                    login();
                }else{
                    Toast.makeText(InicioSesion.this, "Rellena los campos solicitados", Toast.LENGTH_SHORT).show();
                }
            }
        });
        
    }

    private void login() {
        auth.signInWithEmailAndPassword(correo,contrasenia).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(InicioSesion.this,PerfilUsuario.class));
                    finish();
                }else{
                    Toast.makeText(InicioSesion.this, "No fue posible iniciar sesion, comprueba los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}