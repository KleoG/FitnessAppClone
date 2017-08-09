package com.example.kleog.fitnessapp.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.example.kleog.fitnessapp.Models.DailyUserInfoModel;
import com.example.kleog.fitnessapp.Models.FoodItemsModel;
import com.example.kleog.fitnessapp.Models.MealModel;
import com.example.kleog.fitnessapp.Models.MealType;
import com.example.kleog.fitnessapp.Models.UserNutritionDB;

import java.net.ConnectException;
import java.util.Date;
import java.util.List;

/**
 * Created by Kevin on 08/08/2017.
 */

public class FoodItemsViewModel extends AndroidViewModel {
    //for use with log.d
    private static final String TAG = "FoodItemsViewModel";
    private final UserNutritionDB appDatabase;

    private LiveData<List<FoodItemsModel>> foods;
    //indicates what meal type the foods in the live data are stores e.g. if foods (above) contains lunch foods then the value of the variable below will be MealType.LUNCH
    private MealType foodTypeCurrentlyInLiveData;

    public FoodItemsViewModel(Application application) {
        super(application);
        appDatabase = UserNutritionDB.getDatabase(this.getApplication());
        foodTypeCurrentlyInLiveData = null;
    }

    public LiveData<List<FoodItemsModel>> getCurrentDayFoodsOfType(MealType type){

        if(foodTypeCurrentlyInLiveData != type){

            foods = appDatabase.FoodItemsModel().getFoodEatenOnDateAndMealType(new Date(), type);
            //updates what the meal type of the foods currently stored
            foodTypeCurrentlyInLiveData = type;
        }

        return foods;

    }

    public FoodItemsModel getCurrentDayFoodWithID(long ID, MealType type) throws Exception{
        try{
            return new RetrieveAsyncTask(appDatabase).execute(ID, type).get();
        }
        catch (Exception e){
            throw new Exception("error trying to retrieve food item with id:" + ID + " meal type: " + type);
        }

    }

    private static class RetrieveAsyncTask extends AsyncTask<Object, Void, FoodItemsModel> {

        private UserNutritionDB db;

        RetrieveAsyncTask(UserNutritionDB appDatabase) {
            db = appDatabase;
        }

        @Override
        /**
         * params[0] = food id
         * params[1] = meal type
         */
        protected FoodItemsModel doInBackground(Object... params) {

            Date date = new Date();


            return db.FoodItemsModel().getSingleFoodItem(date, (MealType) params[1], (long) params[0]);
        }

    }
}
