package com.recordatoriodemedicamentos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.recordatoriodemedicamentos.Fragment.DoctoresFragment;
import com.recordatoriodemedicamentos.Fragment.MedicinasFragment;
import com.recordatoriodemedicamentos.Fragment.PacientesFragment;
import com.recordatoriodemedicamentos.Fragment.PrincipalFragment;
import com.recordatoriodemedicamentos.Modelo.AuthProvider;
import com.recordatoriodemedicamentos.Notificacion.Recordatorios;

public class PrincipalActivity extends AppCompatActivity {

    AuthProvider authProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        getSupportActionBar().setTitle("Recordatorio");

        authProvider = new AuthProvider();

        BottomNavigationView bottomNav = findViewById(R.id.btnNavegador);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new PrincipalFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        case R.id.nav_principal:
                            selectedFragment = new PrincipalFragment();
                            break;
                        case R.id.nav_pacientes:
                            selectedFragment = new PacientesFragment();
                            break;
                        case R.id.nav_medicamentos:
                            selectedFragment = new MedicinasFragment();
                            break;
                        case R.id.nav_consultorios:
                            selectedFragment = new DoctoresFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };

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
        }
        return super.onOptionsItemSelected(item);
    }
}
