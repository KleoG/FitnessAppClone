package com.example.kleog.fitnessapp.ViewModels;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.MainThread;
import android.util.Log;

import com.example.kleog.fitnessapp.Models.DailyUserInfoModel;
import com.example.kleog.fitnessapp.Models.UserNutritionDB;
import com.jjoe64.graphview.series.DataPoint;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

/**
 * Created by Kevin on 19/08/2017.
 */

public class MainActivityGraphViewModel extends AndroidViewModel {

    private static final String TAG = "GraphViewModel";

    private final UserNutritionDB appDatabase;

    private CaloriesDisplayedLiveData caloriesDisplayed;

    private AsyncTask<Double, Void, Void> updatingCaloriesDisplayed;

    @SuppressLint("StaticFieldLeak")
    public MainActivityGraphViewModel(Application application) {
        super(application);

        appDatabase = UserNutritionDB.getDatabase(this.getApplication());



        //sets the calories to be displayed as what is already in the database


    }

    public CaloriesDisplayedLiveData getCaloriesDisplayed(){

        if(caloriesDisplayed == null){

            Double initialCalories = null;
            try {
                initialCalories = new AsyncTask<Void, Void, Double>(){
                    @Override
                    protected Double doInBackground(Void... params) {

                        try {
                            return appDatabase.DailyUserInfoModel().getDate(new Date()).getTotalCalories();
                        }
                        catch (Exception e){
                            return null;
                        }

                    }
                }.execute().get();
            } catch (Exception e) {
            }

            caloriesDisplayed = CaloriesDisplayedLiveData.get(initialCalories);
            Log.d(TAG, "MainActivityGraphViewModel: calories displayed value has been changed");
            caloriesDisplayed.changeValue(initialCalories);
        }
        return caloriesDisplayed;
    }

    @SuppressLint("StaticFieldLeak")
    public void changeInCalories(Double newCalories){
        if (updatingCaloriesDisplayed == null){
            updatingCaloriesDisplayed = new AsyncTask<Double, Void, Void>() {

                @Override
                protected Void doInBackground(Double... params) {

                    //params[0] = newCalories

                    Log.d(TAG, "doInBackground: calories: " + params[0] + ", old calories: " + caloriesDisplayed.getValue());
                    //if graph is increasing
                    if(params[0] - caloriesDisplayed.getValue() > 0.0) {
                        while(!Objects.equals(caloriesDisplayed.getValue(), params[0])){
                            Log.d(TAG, "doInBackground: calories displayed: " + caloriesDisplayed.getValue());
                            caloriesDisplayed.changePostValue(caloriesDisplayed.getValue() + 1);

                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }


                    }else{ //graph is decreasing
                        while(!Objects.equals(caloriesDisplayed.getValue(), params[0])){
                            Log.d(TAG, "doInBackground: calories displayed: " + caloriesDisplayed);
                            caloriesDisplayed.changePostValue(caloriesDisplayed.getValue() - 1);
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }


                    }
                    return null;
                }
            };

            updatingCaloriesDisplayed.execute(newCalories);
        }else{
            //if a async task is current updating the UI
            if(updatingCaloriesDisplayed.getStatus() == AsyncTask.Status.RUNNING){
                Log.d(TAG, "changeInCalories: Async Task was running and now got cancelled");
                updatingCaloriesDisplayed.cancel(true);

                updatingCaloriesDisplayed.execute(newCalories);

            }
            else if(updatingCaloriesDisplayed.getStatus() == AsyncTask.Status.FINISHED){
                Log.d(TAG, "changeInCalories: Async Task was finished, now retarting");

                updatingCaloriesDisplayed.execute(newCalories);
            }
        }
    }

    private static class GraphAnimatorAsyncTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }



    /**
     * live data for the value to be displayed on the graph
     */
    private static class CaloriesDisplayedLiveData extends LiveData<Double> {
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

        public void changeValue(Double value){
            caloriesDisplayed = value;
            setValue(value);
        }

        public void changePostValue(Double value){
            caloriesDisplayed = value;
            postValue(value);
        }

        public Double getValue(){
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
