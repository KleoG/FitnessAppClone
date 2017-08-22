package com.example.kleog.fitnessapp.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.MainThread;
import android.util.Log;

import com.example.kleog.fitnessapp.Models.DailyUserInfoModel;
import com.example.kleog.fitnessapp.Models.UserNutritionDB;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.Date;
import java.util.Objects;

/**
 * Created by Kevin on 19/08/2017.
 */

public class MainActivityGraphViewModel extends AndroidViewModel {

    private static final String TAG = "GraphViewModel";

    private final UserNutritionDB appDatabase;

    private CaloriesDisplayedLiveData caloriesDisplayed;

    private AsyncTask<Double, Void, Void> updatingCaloriesDisplayed;


    public MainActivityGraphViewModel(Application application) {
        super(application);

        appDatabase = UserNutritionDB.getDatabase(this.getApplication());


        //sets the calories to be displayed as what is already in the database

        getCaloriesDisplayed();

    }

    public CaloriesDisplayedLiveData getCaloriesDisplayed() {

        if (caloriesDisplayed == null) {

            Double initialCalories = null;

            try {
                initialCalories = new RetrieveCurrentDayAsyncTask(appDatabase).execute().get().getTotalCalories();
            } catch (Exception e) {
                Log.d(TAG, "getCaloriesDisplayed: error retrieving initial calories");
            }

            //value must be rounded to avoid infinite loop
            initialCalories = ((Long) Math.round(initialCalories)).doubleValue();

            caloriesDisplayed = CaloriesDisplayedLiveData.get(initialCalories);
            caloriesDisplayed.changePostValue(initialCalories);

        }
        return caloriesDisplayed;
    }

    private static class RetrieveCurrentDayAsyncTask extends AsyncTask<Void, Void, DailyUserInfoModel> {
        private UserNutritionDB db;

        RetrieveCurrentDayAsyncTask(UserNutritionDB appDatabase) {
            db = appDatabase;

        }

        @Override
        protected DailyUserInfoModel doInBackground(Void... params) {
            return db.DailyUserInfoModel().getDate(new Date());

        }
    }

    public void changeInCalories(Double newCalories, BarGraphSeries<DataPoint> series) {
        //if no async task created then create one
        if (updatingCaloriesDisplayed == null) {
            Log.d(TAG, "changeInCalories: creating async task to update graph");
            updatingCaloriesDisplayed = new GraphAnimatorAsyncTask(caloriesDisplayed, series);

            updatingCaloriesDisplayed.execute(newCalories);

        } else {// if an async task was already created check if it is running or finished

            //if a async task is currently updating the UI
            if (updatingCaloriesDisplayed.getStatus() == AsyncTask.Status.RUNNING) {
                Log.d(TAG, "changeInCalories: Async Task was running and now got cancelled");

                //cancel the current task and create a new one
                updatingCaloriesDisplayed.cancel(true);

                updatingCaloriesDisplayed = new GraphAnimatorAsyncTask(caloriesDisplayed, series);

                updatingCaloriesDisplayed.execute(newCalories);

            } else if (updatingCaloriesDisplayed.getStatus() == AsyncTask.Status.FINISHED) {
                Log.d(TAG, "changeInCalories: Async Task was finished, now restarting with a new async task");
                updatingCaloriesDisplayed = new GraphAnimatorAsyncTask(caloriesDisplayed, series);

                updatingCaloriesDisplayed.execute(newCalories);
            }
        }
    }

    /**
     * An async task which updates the mainActivity graph in the background
     */
    private static class GraphAnimatorAsyncTask extends AsyncTask<Double, Void, Void> {

        private CaloriesDisplayedLiveData caloriesDisplayed;
        private BarGraphSeries<DataPoint> series;

        GraphAnimatorAsyncTask(CaloriesDisplayedLiveData liveData, BarGraphSeries<DataPoint> series) {
            caloriesDisplayed = liveData;
            this.series = series;
        }

        @Override
        protected Void doInBackground(Double... params) {

            //params[0] = newCalories
            //round the number to a whole number to prevent infinite loops
            params[0] = ((Long) Math.round(params[0])).doubleValue();

            int rateOfChange;

            //this needs to be here for when onCreate is called for the main activity to initially set the graph
            series.resetData(new DataPoint[]{new DataPoint(0, caloriesDisplayed.getValue())});

            Log.d(TAG, "doInBackground: new calories: " + params[0] + ", old calories: " + caloriesDisplayed.getValue());
            //if graph is increasing
            if (params[0] - caloriesDisplayed.getValue() > 0.0) {
                while (!Objects.equals(caloriesDisplayed.getValue(), params[0])) {
                    //Log.d(TAG, "doInBackground: calories displayed: " + caloriesDisplayed.getValue());

                    //the difference in target calories vs the calories currently on screen
                    double difference = params[0] - caloriesDisplayed.getValue();

                    rateOfChange = rateOfChangeBasedOnDifference(difference);

                    caloriesDisplayed.changePostValue(caloriesDisplayed.getValue() + rateOfChange);

                    series.resetData(new DataPoint[]{new DataPoint(0, caloriesDisplayed.getValue())});
                    //Log.d(TAG, "doInBackground: data displayed on graph should be: " + series.getValues(0,0).next().getY());


                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


            } else { //graph is decreasing
                while (!Objects.equals(caloriesDisplayed.getValue(), params[0])) {
                    //Log.d(TAG, "doInBackground: calories displayed: " + caloriesDisplayed.getValue());

                    //the difference in target calories vs the calories currently on screen
                    double difference = caloriesDisplayed.getValue() - params[0];

                    rateOfChange = rateOfChangeBasedOnDifference(difference);

                    caloriesDisplayed.changePostValue(caloriesDisplayed.getValue() - rateOfChange);

                    series.resetData(new DataPoint[]{new DataPoint(0, caloriesDisplayed.getValue())});

                    //Log.d(TAG, "doInBackground: data displayed on graph should be: " + series.getValues(0,0).next().getY());

                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


            }
            return null;
        }

        /**
         * @param difference the difference between what value is currently shown on the graph and the target value
         * @return the value that the graph should change by each tick
         */
        private int rateOfChangeBasedOnDifference(Double difference) {
            //rate determines how fast the graph will change speed (lower number = higher rate of change)

            //DO NOT MAKE IT A VALUE OF 1
            int rate = 20;

            return 1 + (int) (Math.floor(difference / rate));
        }
    }


    /**
     * live data which holds the value of the data displayed on the graph
     */
    public static class CaloriesDisplayedLiveData extends LiveData<Double> {
        private static CaloriesDisplayedLiveData sInstance;

        private Double caloriesDisplayed;

        private CaloriesDisplayedLiveData(Double caloriesDisplayed) {
            this.caloriesDisplayed = caloriesDisplayed;
        }

        @MainThread
        public static CaloriesDisplayedLiveData get(Double caloriesDisplayed) {
            if (sInstance == null) {
                sInstance = new CaloriesDisplayedLiveData(caloriesDisplayed);
            }
            return sInstance;
        }

        public void changeValue(Double value) {
            caloriesDisplayed = value;
            setValue(value);
        }

        void changePostValue(Double value) {
            caloriesDisplayed = value;
            postValue(value);
        }

        public Double getValue() {
            return caloriesDisplayed;
        }


        @Override
        protected void onActive() {
        }

        @Override
        protected void onInactive() {
        }

    }

}
