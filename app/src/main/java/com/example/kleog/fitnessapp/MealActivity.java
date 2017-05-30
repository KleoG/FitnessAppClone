package com.example.kleog.fitnessapp;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kleog.fitnessapp.UserNutritionDatabase.DailyUserInfoModel;
import com.example.kleog.fitnessapp.UserNutritionDatabase.FoodItemsModel;
import com.example.kleog.fitnessapp.UserNutritionDatabase.UserNutritionDB;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

/**
 * Created by Kevin on 23/05/2017.
 */

public class MealActivity extends AppCompatActivity {
    public UserNutritionDB db;

    public String mealType;

    ListView foodListView;

    ArrayList<FoodItemsModel> foodItemsList;

    ArrayAdapter<FoodItemsModel> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //sets the title of the page to the button pressed
        setContentView(R.layout.activity_meal_page);
        mealType = getIntent().getStringExtra("MEAL_TYPE");

        setTitle(mealType);

        db = UserNutritionDB.getDatabase(this.getApplicationContext());

        foodListView = (ListView) findViewById(R.id.foodList);
        foodItemsList = new ArrayList<>();
        adapter = new ArrayAdapter<FoodItemsModel>(this,
                android.R.layout.simple_list_item_1,
                foodItemsList);
        foodListView.setAdapter(adapter);

    }

    public void onClickNotFinished(View view){
        //first thread for inserting data
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
                try {
                    //value returned by the async task is passed into info
                    info = new AsyncTask<Void, Void, String>(){
                        @Override
                        protected String doInBackground(Void... params){

                            //Log.d("MEAL_ACTIVITY", "doInBackground: retriving information: params[0]: " + params[0]);
                            return db.DailyUserInfoModel().getDate(new Date()).getWeight().toString();
                        }

                    }.execute().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), info, Toast.LENGTH_LONG).show();


            }
        }.execute();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    public void updateFoodEatenList(){

    }



}
