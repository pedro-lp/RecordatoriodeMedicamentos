package com.recordatoriodemedicamentos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.mbms.MbmsErrors;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.signin.SignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.recordatoriodemedicamentos.Modelo.AuthProvider;
import com.recordatoriodemedicamentos.Modelo.IMedicamento;
import com.recordatoriodemedicamentos.Modelo.Medicamento;
import com.recordatoriodemedicamentos.Modelo.MedicamentoAdapter;
import com.recordatoriodemedicamentos.Modelo.MedicamentoProvider;

import java.util.ArrayList;
import java.util.List;

public class PrincipalActivity extends AppCompatActivity implements IMedicamento {
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogle;
    private GoogleSignInOptions gso;
    AuthProvider authProvider;
    MedicamentoProvider mediProvider;
    ArrayList<Medicamento> medicamentoArrayList;
    RecyclerView idrecyclerView;
    private MedicamentoAdapter medicamentoAdapter;

    SharedPreferences preMed;
    SharedPreferences.Editor edtMed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        //MyToolbar.show(this, "Usuario", false);

        authProvider = new AuthProvider();
        mediProvider = new MedicamentoProvider();

        preMed = getApplicationContext().getSharedPreferences("typeBoton",MODE_PRIVATE);
        edtMed = preMed.edit();

        idrecyclerView = (RecyclerView) findViewById(R.id.rcvlistaMedicamentos);
        medicamentoArrayList = new ArrayList<>();
        medicamentoAdapter = new MedicamentoAdapter(this, medicamentoArrayList);
        idrecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        idrecyclerView.setAdapter(medicamentoAdapter);

        mediProvider.showMedicamento(authProvider.getId(),medicamentoAdapter,medicamentoArrayList);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //Datos del usuario
        String nombre = currentUser.getDisplayName();
        String correo = currentUser.getEmail();
        String id = currentUser.getUid();

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogle = GoogleSignIn.getClient(this, gso);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_usuario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            //authProvider.logout();
            //Intent intent = new Intent(PrincipalActivity.this, MainActivity.class);
            //startActivity(intent);
           // finish();
            //Cerrar sesion en firebase
            mAuth.signOut();
            //Cerrar sesion con google
            mGoogle.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(i);
                        PrincipalActivity.this.finish();
                    }else{
                        Toast.makeText(PrincipalActivity.this, "Error al cerrar la sesion", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        if (item.getItemId() == R.id.notificacion) {
            Intent intent = new Intent(PrincipalActivity.this, Recordatorios.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view){
        Intent miIntent = null;
        switch (view.getId()){
            case R.id.btnAgrMed:
                edtMed.putString("boton","agregar");
                edtMed.apply();
                Intent intent = new Intent(PrincipalActivity.this, MedicamentoActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void OpcionEditar(Medicamento medicamento) {
        edtMed.putString("boton","modificar");
        edtMed.apply();
        Intent intent = new Intent(PrincipalActivity.this, MedicamentoActivity.class);
        intent.putExtra("medicamento",medicamento);
        startActivity(intent);

    }

    @Override
    public void OpcionEliminar(Medicamento medicamento) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("Mensaje de Advertencia");
        alerta.setMessage("¿Esta seguro que desea Eliminar este Contacto?");
        alerta.setCancelable(false);
        alerta.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mediProvider.removeMedicamento(authProvider.getId(),medicamento,medicamentoAdapter);
                Toast.makeText(PrincipalActivity.this, "Se Elimino Correctamente el Medicamento", Toast.LENGTH_SHORT).show();
            }
        });
        alerta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alerta.show();
    }

}
