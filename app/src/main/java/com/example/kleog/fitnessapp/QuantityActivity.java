package com.example.kleog.fitnessapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.kleog.fitnessapp.R;

public class QuantityActivity extends AppCompatActivity {

    private static final int MAX_QUANTITY = 100;

    private String foodItem;

    private int quantity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quantity);

        quantity = 0;
        // gets what type of meal e.g. breakfast, lunch, dinner or snack
        foodItem = getIntent().getStringExtra("FOOD_ITEM");

        setTitle(foodItem);
        updateQuantityOnScreen();
    }

    public void updateQuantityOnScreen(){
        // must change the quanity which is in an integer to String before setText
        ((TextView)findViewById(R.id.textView)).setText(String.valueOf(quantity));
    }

    public void increaseQuantity(View view){
        quantity++;
        updateQuantityOnScreen();
    }

    public void decreaseQuantity(View view){
        quantity--;
        updateQuantityOnScreen();
    }

}
