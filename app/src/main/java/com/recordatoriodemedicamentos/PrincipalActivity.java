package com.recordatoriodemedicamentos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PrincipalActivity extends AppCompatActivity {
    Button btnCerrarSesion, btnSalirPerfil;
    TextView usuario;
    FirebaseAuth auth;
    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        MyToolbar.show(this, "Usuario", false);

        btnCerrarSesion = (Button) findViewById(R.id.btnCerrarSesion);
        btnSalirPerfil = (Button) findViewById(R.id.btnSalirPerfil);
        usuario = (TextView) findViewById(R.id.lblUsuario);
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();
        informacion();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_usuario, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_logout){
            auth.signOut();
            Intent intent = new Intent(PrincipalActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSalirPerfil:
                //startActivity(new Intent(PerfilUsuario.this, MainActivity.class));
                Intent i = new Intent(Intent.ACTION_MAIN);
                i.addCategory(Intent.CATEGORY_HOME);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                break;
            case R.id.btnCerrarSesion:
                auth.signOut();
                startActivity(new Intent(PrincipalActivity.this, MainActivity.class));
                finish();
                break;
        }
    }

    private void informacion() {
        String id = auth.getCurrentUser().getUid();
        db.child("usuarios").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String nombre = snapshot.child("nombre").getValue().toString();
                    usuario.setText(nombre);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
