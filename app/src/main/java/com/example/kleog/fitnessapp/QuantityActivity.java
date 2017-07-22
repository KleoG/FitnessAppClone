package com.example.kleog.fitnessapp;

import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class QuantityActivity extends AppCompatActivity {

    public static final int MAX_QUANTITY = 100;
    public static final int MIN_QUANTITY = 0;

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

    // must change the quantity which is in an integer to String before setText
    public void updateQuantityOnScreen() {
        ((TextView) findViewById(R.id.textView)).setText(String.valueOf(quantity));
    }

    public void increaseQuantity(View v) {
        quantity = Math.min(MAX_QUANTITY, quantity + 1);
        updateQuantityOnScreen();
    }

    public void decreaseQuantity(View v) {
        quantity = Math.max(MIN_QUANTITY, quantity - 1);
        updateQuantityOnScreen();
    }

    // called when submit button is pressed - has not been linked to button yet
    public void submitQuantity(View view) {

    }

    public int getQuantity() {
        return quantity;
    }

    @VisibleForTesting
    public void setQuantity(int qty) {
        quantity = qty;
    }
}
