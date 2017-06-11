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

import com.example.kleog.fitnessapp.UserNutritionDatabase.UserNutritionDB;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

public class MainActivity extends AppCompatActivity {
    UserNutritionDB db;

    GraphView calorieGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = UserNutritionDB.getDatabase(this);

        calorieGraph = (GraphView) findViewById(R.id.calorieBarPlaceholder);
//        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
//                new DataPoint(0, 200),
//                //new DataPoint(1, 100)
//        });

        BarGraphSeries<DataPoint> series = new BarGraphSeries<>();

//        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
//            @Override
//            public int get(DataPoint data) {
//                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
//            }
//        });
        calorieGraph.setTitle("calories eaten");
        //draws value of bar directly ontop of the bar
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.BLUE);
        series.setSpacing(0);

        calorieGraph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE); // background grids get removed

        calorieGraph.getViewport().setYAxisBoundsManual(true);
        calorieGraph.getViewport().setMaxYAxisSize(2000);
        calorieGraph.getViewport().setMaxY(2000);

        calorieGraph.getGridLabelRenderer().setHorizontalLabelsVisible(false);  // removes x axis and line
        calorieGraph.getGridLabelRenderer().setVerticalLabelsVisible(false);    // removes y axis and line
        // remove vertical labels and lines



        series.appendData(new DataPoint(0, 400), true, 2000);
        //series.appendData(new DataPoint(1, 300), true, 2000);


        calorieGraph.addSeries(series);


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
