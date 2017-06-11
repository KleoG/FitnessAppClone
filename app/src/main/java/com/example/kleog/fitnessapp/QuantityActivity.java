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

    // must change the quantity which is in an integer to String before setText
    public void updateQuantityOnScreen(){
        ((TextView)findViewById(R.id.textView)).setText(String.valueOf(quantity));
    }

    public void increaseQuantity(View view){
        //quantity++;
        setQuantity(1);
        updateQuantityOnScreen();
    }

    public void decreaseQuantity(View view){
        //quantity--;
        setQuantity(-1);
        updateQuantityOnScreen();
    }

    // called when submit button is pressed - has not been linked to button yet
    public void submitQuantity(View view){

    }

    public int getQuantity(){
        return quantity;
    }

    // sends through the change in quantity (-1 for decrease, 1 for increase)
    // creates a local variable based on what the current quantity is.
    // applies the change, and checks whether change is within limit
    // if yes, then change will be applied to instance field.
    // otherwise, quantity stays the same
    public void setQuantity(int quantityChange){
        int quantity = this.quantity;
        quantity+=quantityChange;

        if(quantity >= 0 && quantity <= 100){
            this.quantity = quantity;
        }

    }

}
