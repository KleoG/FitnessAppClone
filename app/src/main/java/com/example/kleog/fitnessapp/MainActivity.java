package com.example.kleog.fitnessapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    UserNutritionDBHelper db;

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = UserNutritionDBHelper.getInstance(this);
        SQLiteDatabase test = db.getWritableDatabase();

        //mTextMessage = (TextView) findViewById(R.id.message);
        //BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    /**
     * is called when the graphs button is clicked
     * @param view object being clicked on - in this case the "graphs" button
     */
    public void goToGraphsPage(View view){
        Intent intent = new Intent(this, GraphPage.class);
        startActivity(intent);
    }

    /**
     * when one of the meal time  buttons is pressed the meal type will be passed to the
     * meal activity and open a session for that particular meal type
     * @param view one of the meal buttons
     */
    public void goToMealActivity(View view){
        Intent i = new Intent(this, MealActivity.class);

        //passes extra information to the MealActivity class
        Button button = (Button) view;
        i.putExtra("MEAL_TYPE", button.getText().toString());

        startActivity(i);
    }

}
