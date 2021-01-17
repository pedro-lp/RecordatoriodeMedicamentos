package com.recordatoriodemedicamentos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;

public class IniciarSesionActivity extends AppCompatActivity {
    TextInputEditText mTextInputEmail;
    TextInputEditText mTextInputPassword;
    Button mButtonLogin;
    private CircleImageView mCircleImageBack;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    AlertDialog mDialog;

    SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);
        //MyToolbar.show(this,"Login usuario",true);

        mPref = getApplicationContext().getSharedPreferences("tipoUsuario", MODE_PRIVATE);

        mTextInputEmail = findViewById(R.id.textInputEmail);
        mTextInputPassword = findViewById(R.id.textInputPassword);
        mButtonLogin = findViewById(R.id.btnEntrarI);
        mCircleImageBack = findViewById(R.id.circleImageBack);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDialog = new SpotsDialog.Builder().setContext(IniciarSesionActivity.this).setMessage("Espere un Momento").build();

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        mCircleImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void login() {
        String email = mTextInputEmail.getText().toString();
        String password = mTextInputPassword.getText().toString();

        if (!email.isEmpty() && !password.isEmpty()) {
            if (password.length() >= 6) {
                mDialog.show();
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String typeUser = mPref.getString("usuario", "");
                            if (typeUser.equals("tutor")) {
                                mDatabase.child("Usuarios").child("Tutores").child(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            Intent intent = new Intent(IniciarSesionActivity.this, PrincipalActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(IniciarSesionActivity.this, "No es un usuario permitido", Toast.LENGTH_SHORT).show();
                                            mAuth.signOut();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            } else if (typeUser.equals("paciente")) {
                                mDatabase.child("Paciente").child(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            Intent intent = new Intent(IniciarSesionActivity.this, PrincipalActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(IniciarSesionActivity.this, "No es un usuario permitido", Toast.LENGTH_SHORT).show();
                                            mAuth.signOut();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }
                                });
                            }
                        } else {
                            Toast.makeText(IniciarSesionActivity.this, "La contraseña o el password son incorrectos", Toast.LENGTH_SHORT).show();
                        }
                        mDialog.dismiss();
                    }
                });
            } else {
                Toast.makeText(IniciarSesionActivity.this, "La contraseña debe tener mas de 6 caracteres", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(IniciarSesionActivity.this, "El Correo electronico y la contraseña son obligatorios", Toast.LENGTH_SHORT).show();
        }
    }
}