package com.example.kleog.fitnessapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.kleog.fitnessapp.UserNutritionDatabase.DailyUserInfoModel;
import com.example.kleog.fitnessapp.UserNutritionDatabase.UserNutritionDB;

import java.util.Date;

/**
 * Created by Kevin on 23/05/2017.
 */

public class MealActivity extends AppCompatActivity {
    public UserNutritionDB db;
    public String mealType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //sets the title of the page to the button pressed
        setContentView(R.layout.activity_meal_page);
        mealType = getIntent().getStringExtra("MEAL_TYPE");

        setTitle(mealType);

        db = UserNutritionDB.getDatabase(this.getApplicationContext());

    }

    public void onClickNotFinished(View view){

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                db.DailyUserInfoModel().insert(new DailyUserInfoModel(new Date(), 150, 100, 75, 50, 73));
                return null;
            }

            @Override
            protected void onPostExecute(Void param) {
                Toast.makeText(getApplicationContext(), "user information was inserted", Toast.LENGTH_LONG).show();
                String info = "user info not found";
                new AsyncTask<String, Void, Void>(){
                    @Override
                    protected Void doInBackground(String... params){
                        params[0] = db.DailyUserInfoModel().getDate(new Date()).toString();
                        return null;
                    }
                }.execute(info);
                Toast.makeText(getApplicationContext(), info, Toast.LENGTH_LONG).show();


            }
        }.execute();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }



}
