package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GradesInput extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private RecyclerView recycler;
    private ArrayList<GradesModel> list = new ArrayList<>();
    private InteractiveTabAdapter adapter;
    private int gradesCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades_input);

        recycler = findViewById(R.id.recyclerView);

        recycler.setLayoutManager(new LinearLayoutManager(this));

        gradesCount = getIntent().getIntExtra("liczba ocen", 5);

        for (int i = 0; i < gradesCount; i++) {
            list.add(new GradesModel("ocena " + i, 2));
        }

        adapter = new InteractiveTabAdapter(this, list);
        recycler.setAdapter(adapter);
        System.out.println(adapter.getItemCount());

        Button button = findViewById(R.id.pr);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float average = 0;
                for (int i = 0; i < gradesCount; i++) {
                    average += list.get(i).getGrade();
                }
                average /= gradesCount;

                Intent resultInt = new Intent();
                resultInt.putExtra("srednia", average);
                setResult(RESULT_OK, resultInt);
                finish();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_grades_input);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        ArrayList<Integer> temp = new ArrayList<>();

        for (int i = 0; i < gradesCount; i++) {
            temp.add(list.get(i).getGrade());
        }

        outState.putIntegerArrayList("oceny", temp);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        ArrayList<Integer> grades = savedInstanceState.getIntegerArrayList("oceny");
        for (int i = 0; i < gradesCount; i++) {
            list.get(i).setGrade(grades.get(i));
        }
    }
}