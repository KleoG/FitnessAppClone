package com.example.kleog.fitnessapp.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.example.kleog.fitnessapp.Models.DailyUserInfoModel;
import com.example.kleog.fitnessapp.Models.MealModel;
import com.example.kleog.fitnessapp.Models.MealType;
import com.example.kleog.fitnessapp.Models.UserNutritionDB;

import java.util.Date;
import java.util.List;

/**
 * Created by Kevin on 22/07/2017.
 */

public class MealModelViewModel extends AndroidViewModel {
    //for user with logd
    private static final String TAG = "MealModelViewModel";
    private final UserNutritionDB appDatabase;

    private LiveData<MealModel> breakfastMeal;
    private LiveData<MealModel> lunchMeal;
    private LiveData<MealModel> dinnerMeal;
    private LiveData<MealModel> snacksMeal;
    private DailyUserInfoViewModel dailyUserInfoVM;

    public MealModelViewModel(Application application) {
        super(application);

        appDatabase = UserNutritionDB.getDatabase(this.getApplication());

        //gets live data for all meals since they are small and should always have data ready
        breakfastMeal = appDatabase.MealModel().getMeal(MealType.BREAKFAST, new Date());

        lunchMeal = appDatabase.MealModel().getMeal(MealType.LUNCH, new Date());

        dinnerMeal = appDatabase.MealModel().getMeal(MealType.DINNER, new Date());

        snacksMeal = appDatabase.MealModel().getMeal(MealType.SNACKS, new Date());

    }

    public LiveData<MealModel> getMealInfo(MealType meal) {

        Log.d("", "getMealInfo: meal type: " + meal + " was requested");
        if (meal == MealType.BREAKFAST) {
            return breakfastMeal;
        } else if (meal == MealType.LUNCH) {
            return lunchMeal;
        } else if (meal == MealType.DINNER) {
            return dinnerMeal;
        } else { //meal type == snacks
            return snacksMeal;
        }
    }

    public void updateMeal(MealModel data) {

        new UpdateAsyncTask(appDatabase).execute(data);
    }

    //private static class created for inserting into database with async task to prevent memory leaks
    private static class InsertAsyncTask extends AsyncTask<MealModel, Void, Void> {

        private UserNutritionDB db;

        InsertAsyncTask(UserNutritionDB appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(MealModel... params) {
            db.MealModel().insert(params[0]);

            return null;
        }

    }

    //private static class created for updating database with async task to prevent memory leaks
    private static class UpdateAsyncTask extends AsyncTask<MealModel, Void, Void> {

        private UserNutritionDB db;

        UpdateAsyncTask(UserNutritionDB appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(MealModel... params) {
            db.MealModel().update(params[0]);

            //also updated the daily user info table
            Date date = params[0].getDate();

            Double totalCarbs = 0.0;
            Double totalProtein = 0.0;
            Double totalFat = 0.0;
            Double totalCalories = 0.0;

            List<MealModel> mealsToday = db.MealModel().getMealsOnDate(date).getValue();

            for (MealModel currentMeal : mealsToday) {
                totalCarbs += currentMeal.getTotalCarbs();
                totalProtein += currentMeal.getTotalProtein();
                totalFat += currentMeal.getTotalFat();
                totalCalories += currentMeal.getTotalCalories();
            }


            Double oldWeight = db.DailyUserInfoModel().getDate(date).getValue().getWeight();

            DailyUserInfoModel newUserInfo = new DailyUserInfoModel(date, totalCalories, totalProtein, totalCarbs, totalFat, oldWeight);

            db.DailyUserInfoModel().update(newUserInfo);

            return null;
        }

    }


}


