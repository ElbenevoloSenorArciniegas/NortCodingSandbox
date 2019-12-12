package com.example.marykay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marykay.dto.Revision;
import com.example.marykay.dto.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class menu extends AppCompatActivity {

    private static final String TAG = "Menu/";
    private FirebaseAuth mAuth;
    FirebaseDatabase database  = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    CardView card1;

    String[] datos = new String[4];
    List<ProgressBar> circulos = new ArrayList<>();
    List<TextView> textos = new ArrayList<>();

    int mutex = datos.length;
    int max = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        myRef = database.getReference(mAuth.getCurrentUser().getUid());

        card1 = findViewById(R.id.card1);
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(menu.this, List_Activity.class));
            }
        });

        circulos.add((ProgressBar) findViewById(R.id.progress1));
        circulos.add((ProgressBar) findViewById(R.id.progress2));
        circulos.add((ProgressBar) findViewById(R.id.progress3));
        circulos.add((ProgressBar) findViewById(R.id.progress4));

        textos.add((TextView) findViewById(R.id.cant1));
        textos.add((TextView) findViewById(R.id.cant2));
        textos.add((TextView) findViewById(R.id.cant3));
        textos.add((TextView) findViewById(R.id.cant4));

        datos[0]="Conformes";
        datos[1]="Pendientes";
        datos[2]="No conformes";
        datos[3]="Canceladas";

        for (int i = 0; i<datos.length; i++){
            readFromDB(datos[i],circulos.get(i),textos.get(i));
        }
        //initializeData();
    }

    public void v(int progress){
        mutex--;
        max+=progress;
        if(mutex==0){
            for (int i = 0; i< circulos.size(); i++){
                ProgressBar b = circulos.get(i);
                b.setMax(max);
                b.setSecondaryProgress(max);
            }
        }
    }

    private void readFromDB(final String child, final ProgressBar currentBar, final TextView t){
        DatabaseReference ref = myRef.child(child);
        // [START read_message]
        // Read from the database
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue().toString();
                int progress = Integer.valueOf(value);
                t.setText(value+"");
                t.setVisibility(View.VISIBLE);
                currentBar.setIndeterminate(false);
                currentBar.setProgress(progress);
                v(progress);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
                Toast.makeText(menu.this, "Failed to read value.",
                        Toast.LENGTH_SHORT).show();
            }
        });
        // [END read_message]
    }

    private void initializeData(){
        User u = new User();
        u.nombre="User";
        for (int i = 0; i <10; i++){
            u.conformes.add(new Revision("cliente1","1 Nov"));
        }
        for (int i = 0; i <5; i++){
            u.noConformes.add(new Revision("cliente1","1 Nov"));
        }
        for (int i = 0; i <2; i++){
            u.canceladas.add(new Revision("cliente1","1 Nov"));
        }
        for (int i = 0; i <1; i++){
            u.pendientes.add(new Revision("cliente1","1 Nov"));
        }
        database.getReference().child("Users").child(mAuth.getCurrentUser().getUid()).setValue(u);
    }
}