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
 * Created by Kevin on 17/07/2017.
 */

public class DailyUserInfoViewModel extends AndroidViewModel {
    //for use with log.d
    private static final String TAG = "DailyUserInfoViewModel";
    private final UserNutritionDB appDatabase;
    private LiveData<DailyUserInfoModel> currentDayUserInfo;
    private LiveData<List<DailyUserInfoViewModel>> listOfDailyUserInfo;

    public DailyUserInfoViewModel(Application application) {
        super(application);

        appDatabase = UserNutritionDB.getDatabase(this.getApplication());

        currentDayUserInfo = getCurrentDayUserInfo();

        listOfDailyUserInfo = null;
    }

    public LiveData<DailyUserInfoModel> getCurrentDayUserInfo() {

        if (currentDayUserInfo == null) {

            currentDayUserInfo = appDatabase.DailyUserInfoModel().getDate(new Date());

            //if new day and no data inserted yet then create and add to database
            if (currentDayUserInfo.getValue() == null) {
                Log.d(TAG, "getCurrentDayUserInfo: creating new entries for new date: " + new Date());

                DailyUserInfoModel emptyCurrentDay = new DailyUserInfoModel(new Date(), 0.0, 0.0, 0.0, 0.0, 0.0);

                new InsertAsyncTask(appDatabase).execute(emptyCurrentDay);

            }
        }
        return currentDayUserInfo;
    }

    public LiveData<List<DailyUserInfoViewModel>> getListOfDailyUserInfo() {
        return listOfDailyUserInfo;
    }

    public void updateCurrentDayUserInfo(DailyUserInfoModel data) {

        new UpdateAsyncTask(appDatabase).execute(data);
    }

    //private static class created for inserting into database with async task to prevent memory leaks
    private static class InsertAsyncTask extends AsyncTask<DailyUserInfoModel, Void, Void> {

        private UserNutritionDB db;

        InsertAsyncTask(UserNutritionDB appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(DailyUserInfoModel... params) {

            Date date = new Date();
            DailyUserInfoModel emptyCurrentDay = new DailyUserInfoModel(date, 0.0, 0.0, 0.0, 0.0, 0.0);

            db.DailyUserInfoModel().insert(params[0]);

            MealModel breakfast = new MealModel(date, MealType.BREAKFAST, 0.0, 0.0, 0.0, 0.0);
            MealModel lunch = new MealModel(date, MealType.LUNCH, 0.0, 0.0, 0.0, 0.0);
            MealModel dinner = new MealModel(date, MealType.DINNER, 0.0, 0.0, 0.0, 0.0);
            MealModel snacks = new MealModel(date, MealType.SNACKS, 0.0, 0.0, 0.0, 0.0);

            db.MealModel().insertAll(breakfast, lunch, dinner, snacks);
            Log.d(TAG, "doInBackground: finished inserting all new database entries for the day");

            return null;
        }

    }

    //private static class created for updating database with async task to prevent memory leaks
    private static class UpdateAsyncTask extends AsyncTask<DailyUserInfoModel, Void, Void> {

        private UserNutritionDB db;

        UpdateAsyncTask(UserNutritionDB appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(DailyUserInfoModel... params) {
            db.DailyUserInfoModel().update(params[0]);

            return null;
        }

    }


}
