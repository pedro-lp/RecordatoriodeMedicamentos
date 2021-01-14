package com.recordatoriodemedicamentos;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectOptionActivity extends AppCompatActivity {
    Button btnLogin;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_option);
        //MyToolbar.show(this, "Seleccionar Opción", true);

        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });
    }

    public void Login() {
        Intent intent = new Intent(SelectOptionActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void Register() {
        Intent intent = new Intent(SelectOptionActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}