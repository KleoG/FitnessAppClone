package com.example.kleog.fitnessapp;

import android.app.ListActivity;
import android.arch.lifecycle.LiveData;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kleog.fitnessapp.UserNutritionDatabase.AmountEatenType;
import com.example.kleog.fitnessapp.UserNutritionDatabase.DailyUserInfoModel;
import com.example.kleog.fitnessapp.UserNutritionDatabase.FoodItemsModel;
import com.example.kleog.fitnessapp.UserNutritionDatabase.MealType;
import com.example.kleog.fitnessapp.UserNutritionDatabase.UserNutritionDB;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Kevin on 23/05/2017.
 */

public class MealActivity extends AppCompatActivity {
    public UserNutritionDB db;

    public String mealType;

    ListView foodListView;

    ArrayList<FoodItemsModel> foodItemsList;

    FoodItemListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //sets the title of the page to the button pressed
        setContentView(R.layout.activity_meal_page);
        mealType = getIntent().getStringExtra("MEAL_TYPE");

        setTitle(mealType);

        db = UserNutritionDB.getDatabase(this.getApplicationContext());

        //sets up the list underneath the add view button
        foodListView = (ListView) findViewById(R.id.foodList);
        foodItemsList = new ArrayList<>();
        adapter = new FoodItemListAdapter(this, foodItemsList);
        foodListView.setAdapter(adapter);


        //this is how to create and insert data into the db with Async task
        //db.DailyUserInfoModel().insert(new DailyUserInfoModel(new Date(), 150, 100, 75, 50, 73));

        //this is the type of value that will be returned (a liveData that contains the list)
        LiveData< List<DailyUserInfoModel> > info = db.DailyUserInfoModel().getAll();

        //use this to get the list from live data
        info.getValue();

        //temporary testing data
        FoodItemsModel item1 = new FoodItemsModel(new Date(), MealType.LUNCH, "Chicken", 200, 50, 10, 5, 200, AmountEatenType.GRAMS);
        FoodItemsModel item2 = new FoodItemsModel(new Date(), MealType.LUNCH, "Eggs", 100, 30, 20, 10, 3, AmountEatenType.UNITS);
        FoodItemsModel item3 = new FoodItemsModel(new Date(), MealType.LUNCH, "rice", 250, 10, 30, 15, 150, AmountEatenType.GRAMS);
        adapter.add(item1);
        adapter.add(item2);
        adapter.add(item3);


    }

    public void onClickNotFinished(View view){
        //first thread for inserting data
        /*
        the commented out code below is used to insert random data into the db then retrieve the data and display a part of it
        leave it commented for now
         */
//        new AsyncTask<Void, Void, Void>() {
//            @Override
//            protected Void doInBackground(Void... params) {
//                db.DailyUserInfoModel().insert(new DailyUserInfoModel(new Date(), 150, 100, 75, 50, 73));
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Void param) {
//                Toast.makeText(getApplicationContext(), "user information was inserted", Toast.LENGTH_LONG).show();
//                String info = "user info not found";
//                try {
//                    //value returned by the async task is passed into info
//                    info = new AsyncTask<Void, Void, String>(){
//                        @Override
//                        protected String doInBackground(Void... params){
//
//                            //Log.d("MEAL_ACTIVITY", "doInBackground: retriving information: params[0]: " + params[0]);
//                            return db.DailyUserInfoModel().getDate(new Date()).getWeight().toString();
//                        }
//
//                    }.execute().get();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                }
//                Toast.makeText(getApplicationContext(), info, Toast.LENGTH_LONG).show();
//
//
//            }
//        }.execute();



        // temp useless adding of food
        FoodItemsModel item1 = new FoodItemsModel(new Date(), MealType.LUNCH, "Chicken", 200, 50, 10, 5, 200, AmountEatenType.GRAMS);
        adapter.add(item1);
    }

    /**
     * is called when the graphs button is clicked
     * @param view object being clicked on - in this case the "graphs" button
     */
    public void goToSearchPage(View view){
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("MEAL_TYPE", mealType);

        startActivity(intent);  // changes page to the intent (graph page)
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    public void updateFoodEatenList(){

    }

}
