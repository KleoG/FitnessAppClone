package com.example.kleog.fitnessapp.Views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.kleog.fitnessapp.R;

public class ExerciseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        setTitle("Exercise");
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
