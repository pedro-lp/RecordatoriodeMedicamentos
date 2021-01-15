package com.recordatoriodemedicamentos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class RecuperarContrasenia extends AppCompatActivity {
    private EditText correo;
    private Button btnRecuperar;
    private String correoElectronico;
    private FirebaseAuth auth;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_contrasenia);
        correo = findViewById(R.id.editCorreo);
        btnRecuperar = (Button) findViewById(R.id.btnRestablecer);
        pd = new ProgressDialog(RecuperarContrasenia.this);
        auth = FirebaseAuth.getInstance();

        btnRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correoElectronico = correo.getText().toString();
                if (!correoElectronico.isEmpty()) {
                    pd.setMessage("Espera un momento...");
                    pd.setCanceledOnTouchOutside(false);
                    pd.show();
                    restablecer();
                } else {
                    Toast.makeText(RecuperarContrasenia.this, "Ingresa tu correo electronico", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void restablecer() {
        auth.setLanguageCode("es");
        auth.sendPasswordResetEmail(correoElectronico).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RecuperarContrasenia.this, "Se envio un link a tu correo para restablecer tu contraseña", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RecuperarContrasenia.this, "Error al enviar el correo a la direccion indicada", Toast.LENGTH_SHORT).show();
                }
                pd.dismiss();
            }
        });
    }
}