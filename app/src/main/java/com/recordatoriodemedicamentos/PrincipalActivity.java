package com.recordatoriodemedicamentos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.recordatoriodemedicamentos.Modelo.AuthProvider;
import com.recordatoriodemedicamentos.Modelo.MedicamentoProvider;

public class PrincipalActivity extends AppCompatActivity {
    AuthProvider authProvider;
    MedicamentoProvider mediProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        MyToolbar.show(this, "Usuario", false);

        authProvider = new AuthProvider();
        mediProvider = new MedicamentoProvider();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_usuario, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            authProvider.logout();
            Intent intent = new Intent(PrincipalActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        if (item.getItemId() == R.id.notificacion) {
            Intent intent = new Intent(PrincipalActivity.this, Recordatorios.class);
            startActivity(intent);
            //finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
