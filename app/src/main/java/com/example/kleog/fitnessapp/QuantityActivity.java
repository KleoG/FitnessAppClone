package com.example.kleog.fitnessapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.kleog.fitnessapp.R;

public class QuantityActivity extends AppCompatActivity {

    private String foodItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quantity);

        // gets what type of meal e.g. breakfast, lunch, dinner or snack
        foodItem = getIntent().getStringExtra("FOOD_ITEM");

        setTitle(foodItem);
    }
}
