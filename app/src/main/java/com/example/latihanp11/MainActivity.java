package com.example.latihanp11;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.latihanp11.adapter.PersonAdapter;
import com.example.latihanp11.helper.DBHelper;
import com.example.latihanp11.model.Person;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.floatingActionBtn);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            startActivity(intent);
        });

        DBHelper helper = new DBHelper(this);
        ArrayList<Person> person = helper.getAllData();

        ListView lvPerson = findViewById(R.id.lv_person);
        PersonAdapter adapter = new PersonAdapter(this, person);
        lvPerson.setAdapter(adapter);

        lvPerson.setOnItemLongClickListener((adapterView, view, i, l) -> {
            Person p = person.get(i);
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Hapus Data "+p.getNama()+"?");
            builder.setPositiveButton("Cancel", null);
            builder.setNegativeButton("Hapus", (dialogInterface, i1) -> {
                helper.delete(p.getId());
                person.remove(p);
                adapter.notifyDataSetChanged();
            });
            builder.create().show();
            return false;
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}