package com.recordatoriodemedicamentos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.recordatoriodemedicamentos.Modelo.AuthProvider;
import com.recordatoriodemedicamentos.Modelo.Usuario;
import com.recordatoriodemedicamentos.Modelo.UsuarioProvider;

import dmax.dialog.SpotsDialog;

public class LoginActivity extends AppCompatActivity {
    AlertDialog mDialog;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogle;
    private SignInButton btnGoogle;
    private UsuarioProvider mClientProvider;
    private AuthProvider mAuthProvider;
    private String id, name, email;
    String TAG = "GoogleSingInLoginActivity";
    int  RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mDialog = new SpotsDialog.Builder().setContext(LoginActivity.this).setMessage("Cargando").build();
        mClientProvider = new UsuarioProvider();
        mAuthProvider = new AuthProvider();

        btnGoogle = (SignInButton) findViewById(R.id.btnGoogle);
        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.show();
                singIn();
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogle = GoogleSignIn.getClient(this, gso);

        mAuth = FirebaseAuth.getInstance();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mDialog.show();
        if(requestCode== RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            if(task.isSuccessful()){
                try{
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    Log.d(TAG, "firebaseAuthWithGoogle: "+account.getId());
                    firebaseAuthWithGoogle(account.getIdToken());
                }catch(Exception e){
                    Log.w(TAG,"Google sign in failed");
                }
            }else{
                Log.d(TAG, "Error, login no exitoso:"+task.getException().toString());
                Toast.makeText(this, ""+task.getException().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d(TAG, "signInWithCredential:success");
                    mDialog.dismiss();
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    name = currentUser.getDisplayName();
                    email = currentUser.getEmail();
                    id = currentUser.getUid();
                    Usuario usuario = new Usuario(id, name, email);
                    create(usuario);
                    startActivity(new Intent(LoginActivity.this, PrincipalActivity.class));
                    LoginActivity.this.finish();
                }else{
                    Log.w(TAG,"signInWithCredential:failure", task.getException());
                }
            }
        });
    }

    private void singIn(){
        Intent intent = mGoogle.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
        mDialog.dismiss();
    }

    public void create(Usuario usuario){
        mClientProvider.create(usuario).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(!task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "No se pudo crear el usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        FirebaseUser user= mAuth.getCurrentUser();
        if(user != null){
            Intent intent = new Intent(LoginActivity.this, PrincipalActivity.class);
            startActivity(intent);
        }
        super.onStart();
    }
}