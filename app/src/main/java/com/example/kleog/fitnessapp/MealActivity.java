package com.example.kleog.fitnessapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Kevin on 23/05/2017.
 */

public class MealActivity extends AppCompatActivity {

    public String mealType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //sets the title of the page to the button pressed
        setContentView(R.layout.activity_meal_page);
        mealType = getIntent().getStringExtra("MEAL_TYPE");

        setTitle(mealType);

    }

    public void onClickNotFinished(View view){
        Toast.makeText(this, "bitch", Toast.LENGTH_LONG).show();
    }

}
