package com.example.kleog.fitnessapp.Views;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.kleog.fitnessapp.Models.DailyUserInfoModel;
import com.example.kleog.fitnessapp.R;
import com.example.kleog.fitnessapp.ViewModels.DailyUserInfoViewModel;
import com.example.kleog.fitnessapp.ViewModels.MainActivityGraphViewModel;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

public class MainActivity extends LifecycleActivity {
    private GraphView calorieGraph;

    private DailyUserInfoViewModel userInfoVM;
    private MainActivityGraphViewModel graphVM;

    //TODO make it so the day is saved as a variable here (and used everywhere else in the app my extension) so if the app is used past midnight it will not reset the day

    // amount of calories shown on graph on main page
    // should retrieve this figure from database to get total calories of person
    private Double calories;
    //used in animating the graph
    private Double oldCalories;
    private Double caloriesDisplayedOngraph;

    private int targetCalories = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calorieGraph = (GraphView) findViewById(R.id.calorieBarPlaceholder);

        BarGraphSeries<DataPoint> series = new BarGraphSeries<>();

        //sets the colour of the graph
        series.setValueDependentColor(data -> {

            //value double 0.0 - 1.0 (0.0 = red, 1.0 = green, 0.5 = yellow)
            float value = 0.0f;

            //equation = -8(x - 1)^2 + 1
            //x = data.getY() / targetCalories
            //equation will make the graph start to turn green at 64% of the target goal
            float x = (float) data.getY() / targetCalories;
            float equationResult = -8 * (float) Math.pow(x - 1, 2) + 1;

            //if the result is less than 0 then keep value as 0
            if (equationResult > 0.0) {
                value = equationResult;
            }

            return Color.HSVToColor(new float[]{value * 120f, 1f, 1f});
        });

        calorieGraph.setTitle("calories eaten");
        //draws value of bar directly ontop of the bar
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.BLUE);
        series.setSpacing(0);

        // background grids get removed
        calorieGraph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);

        calorieGraph.getViewport().setYAxisBoundsManual(true);
        calorieGraph.getViewport().setMaxYAxisSize(2000);
        calorieGraph.getViewport().setMaxY(2000);

        // remove vertical labels and lines
        calorieGraph.getGridLabelRenderer().setHorizontalLabelsVisible(false);  // removes x axis and line
        calorieGraph.getGridLabelRenderer().setVerticalLabelsVisible(false);    // removes y axis and line


        calorieGraph.addSeries(series);

        //attached to the view model
        userInfoVM = ViewModelProviders.of(this).get(DailyUserInfoViewModel.class);
        graphVM = ViewModelProviders.of(this).get(MainActivityGraphViewModel.class);


        LiveData<DailyUserInfoModel> dailyUserLiveData = userInfoVM.getCurrentDayUserInfo();

        //when changes to the data are made the UI will be updated here
        dailyUserLiveData.observe(this, info -> {
            Log.d("LIVE_DATA_OBSERVER", "onCreate: observed change in live data");

            assert info != null;
            calories = info.getTotalCalories();

            Log.d("LIVE_DATA_OBSERVER", "onCreate: info details: " + info);
            Log.d("LIVE_DATA_OBSERVER", "onCreate: calories: " + calories);

            //update graph
            graphVM.changeInCalories(calories, series);


            Log.d("LIVE_DATA_OBSERVER", "onCreate: UI graph updated");

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

    @Override
    public void onBackPressed() {

    }

    /**
     * is called when the exercise button is clicked
     *
     * @param view object being clicked on - in this case the "exercise" button
     */
    public void goToExerciseActivityPage(View view) {
        Intent intent = new Intent(this, ExerciseActivity.class);
        startActivity(intent);  // changes page to the intent (graph page)

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
