package com.example.kleog.fitnessapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

public class MainActivity extends AppCompatActivity {
    UserNutritionDBHelper db;

    GraphView calorieGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = UserNutritionDBHelper.getInstance(this);
        SQLiteDatabase test = db.getWritableDatabase();

        calorieGraph = (GraphView) findViewById(R.id.calorieBarPlaceholder);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 200),
                new DataPoint(1, 0)
        });
        calorieGraph.addSeries(series);


//        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
//            @Override
//            public int get(DataPoint data) {
//                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
//            }
//        });
        calorieGraph.setTitle("calories eaten");
        //draws value of bar directly ontop of the bar
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.RED);
        //TODO graph: remove values from X and Y axis
        //TODO graph: remove background
        //TODO graph: make bar not always take up the whole Y axis, rather it should start from bottom and increase as user adds calories

    }

    /**
     * is called when the graphs button is clicked
     * @param view object being clicked on - in this case the "graphs" button
     */
    public void goToGraphsPage(View view){
        Intent intent = new Intent(this, GraphPage.class);
        startActivity(intent);  // changes page to the intent (graph page)
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
