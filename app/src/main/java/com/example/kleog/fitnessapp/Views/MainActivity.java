package com.example.kleog.fitnessapp.Views;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.kleog.fitnessapp.Models.UserNutritionDB;
import com.example.kleog.fitnessapp.R;
import com.example.kleog.fitnessapp.ViewModels.DailyUserInfoViewModel;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

public class MainActivity extends LifecycleActivity {
    private UserNutritionDB db;

    private GraphView calorieGraph;

    private DailyUserInfoViewModel userInfoVM;

    //TODO make it so the day is saved as a variable here (and used everywhere else in the app my extension) so if the app is used past midnight it will not reset the day

    // amount of calories shown on graph on main page
    // should retrieve this figure from database to get total calories of person
    private Double calories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = UserNutritionDB.getDatabase(this);


        calorieGraph = (GraphView) findViewById(R.id.calorieBarPlaceholder);

        BarGraphSeries<DataPoint> series = new BarGraphSeries<>();

        /**
         * commented code below can change the colour of the graph
         */
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


        series.appendData(new DataPoint(0, 0), true, 2000);
        //series.appendData(new DataPoint(1, 300), true, 2000);


        calorieGraph.addSeries(series);

        //attached to the view model
        userInfoVM = ViewModelProviders.of(this).get(DailyUserInfoViewModel.class);

        //when changes to the data are made the UI will be updated here
        userInfoVM.getCurrentDayUserInfo().observe(this, info -> {
            Log.d("LIVE_DATA_OBSERVER", "onCreate: observed change in live data");

            calorieGraph.getSeries().clear();

            calories = info.getTotalCalories();

            series.resetData(new DataPoint[]{new DataPoint(0, calories)});

            calorieGraph.addSeries(series);

            Log.d("DATABASE", "onCreate: total items in database: " + info);

        });

    }

    /**
     * is called when the graphs button is clicked
     *
     * @param view object being clicked on - in this case the "graphs" button
     */
    public void goToGraphsPage(View view) {
        Intent intent = new Intent(this, newGraphActivity.class);
        startActivity(intent);  // changes page to the intent (graph page)
    }

    /**
     * is called when the exercise button is clicked
     *
     * @param view object being clicked on - in this case the "exercise" button
     */
    public void goToExerciseActivityPage(View view) {
        Intent intent = new Intent(this, ExerciseActivity.class);
        startActivity(intent);  // changes page to the intent (graph page)

        //temporary for testing
//        Random r = new Random();
//        Double randomCalories = r.nextInt(2000 - 100) + 100.0;
//        DailyUserInfoModel test = new DailyUserInfoModel(new Date(), randomCalories, 0.0, 0.0, 0.0, 0.0);
//        userInfoVM.updateCurrentDayUserInfo(test);

    }

    /**
     * when one of the meal time  buttons is pressed the meal type will be passed to the
     * meal activity and open a session for that particular meal type
     *
     * @param view one of the meal buttons
     */
    public void goToMealActivity(View view) {
        Intent i = new Intent(this, MealActivity.class);

        //passes extra information to the MealActivity class
        Button button = (Button) view;
        i.putExtra("MEAL_TYPE", button.getText().toString());

        startActivity(i);
    }

}
