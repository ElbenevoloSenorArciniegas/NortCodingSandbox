package com.example.marykay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.marykay.Adapter.RVAdapter;
import com.example.marykay.dto.Person;

import java.util.ArrayList;
import java.util.List;

public class List_Activity extends AppCompatActivity {

    private List<Person> persons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_);
        RecyclerView rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        initializeData();
        rv.setAdapter(new RVAdapter(persons));
    }

    // This method creates an ArrayList that has three Person objects
// Checkout the project associated with this tutorial on Github if
// you want to use the same images.
    private void initializeData(){
        persons = new ArrayList<>();
        persons.add(new Person("Emma Wilson", "23 years old", R.drawable.sin_t_tulo));
        persons.add(new Person("Lavery Maiss", "25 years old", R.drawable.sin_t_tulo));
        persons.add(new Person("Lillie Watts", "35 years old", R.drawable.sin_t_tulo));
    }
}
