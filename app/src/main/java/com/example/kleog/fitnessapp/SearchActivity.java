package com.example.kleog.fitnessapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SearchActivity extends AppCompatActivity {

    private String mealType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // gets what type of meal e.g. breakfast, lunch, dinner or snack
        mealType = getIntent().getStringExtra("MEAL_TYPE");

        setTitle(mealType);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
