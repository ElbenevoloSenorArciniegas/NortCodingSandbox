package com.example.marykay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private static final String TAG = "Login/";
    private FirebaseAuth mAuth;
    private TextInputEditText email;
    private TextInputEditText password;
    private Button btn;
    private MaterialButton btn_registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btn = findViewById(R.id.login);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        btn_registro = findViewById(R.id.registro);
        /*btn_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registro();
            }
        });*/
btn_registro.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            abriCosasRaras();
        }
    });
    }

    private void abriCosasRaras() {
        startActivity(new Intent(Login.this,menu.class));
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null) {
            abrir(currentUser.getDisplayName());
        }
    }

    private void login(){
        try {
            mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(Login.this, R.string.bienvenida,
                                        Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                abrir(user.getDisplayName());
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(Login.this, R.string.login_failed,
                                        Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }
                    });
        }catch (IllegalArgumentException ex){
            Toast.makeText(Login.this, R.string.llenarCampos,
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void registro() {
        try{
            String em = email.getText().toString();
            String pass = password.getText().toString();
            if(em.contains("@") && em.contains(".")) {
                if (pass.length() > 5) {
                    mAuth.createUserWithEmailAndPassword(em, pass)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "createUserWithEmail:success");
                                        Toast.makeText(Login.this, R.string.registroExitoso,
                                                Toast.LENGTH_SHORT).show();
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        abrir(user.getDisplayName());
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(Login.this, R.string.registro_failed,
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(Login.this, R.string.invalid_password,
                            Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(Login.this, R.string.invalid_username,
                        Toast.LENGTH_SHORT).show();
            }
    }catch (IllegalArgumentException ex){
        Toast.makeText(Login.this, R.string.llenarCampos,
                Toast.LENGTH_SHORT).show();
    }
    }

    private void abrir(String user){
        //startActivity(new Intent(Login.this,MainActivity.class));
        startActivity(new Intent(Login.this,menu.class));
    }
}
