package com.example.kleog.fitnessapp;

import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.fatsecret.platform.model.Food;
import com.fatsecret.platform.services.android.Request;
import com.fatsecret.platform.services.android.ResponseListener;

public class QuantityActivity extends AppCompatActivity {

    public static final int MAX_QUANTITY = 100;
    public static final int MIN_QUANTITY = 0;

    private String mMealType;
    private int mQuantity;


    private ProgressBar mLoadingIcon;

    //api
    String key = "9363b5d78a9342818602505dad0b01cb";
    String secret = "02d257d83e6249fd98d20782992c0de3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quantity);

        // gets what type of meal e.g. breakfast, lunch, dinner or snack
        mMealType = getIntent().getStringExtra("MEAL_TYPE");


        mLoadingIcon = (ProgressBar) findViewById(R.id.foodProgressBar);
        mLoadingIcon.setVisibility(View.VISIBLE);

        //fat secret API stuff

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Listener listener = new Listener();

        Request req = new Request(key, secret, listener);

        //converts string to long
        long foodID = Long.parseLong( getIntent().getStringExtra("FOOD_ID") );

        req.getFood(requestQueue, foodID);




        mLoadingIcon.setVisibility(View.INVISIBLE);
    }

    // called when submit button is pressed - has not been linked to button yet
    public void submitQuantity(View view) {

    }

    public int getQuantity() {
        return mQuantity;
    }

    @VisibleForTesting
    public void setQuantity(int qty) {
        mQuantity = qty;
    }





    private class Listener implements ResponseListener {
        @Override
        public void onFoodResponse(Food food) {

        }


    }

}
