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

            //if new day and no data inserted yet then create and add to database
            if (retrieveCurrentDayUserInfo() == null) {
                Log.d(TAG, "getCurrentDayUserInfo: creating new entries for new date: " + new Date());

                DailyUserInfoModel emptyCurrentDay = new DailyUserInfoModel(new Date(), 0.0, 0.0, 0.0, 0.0, 0.0);
                try {
                    new InsertAsyncTask(appDatabase).execute(emptyCurrentDay).get(); //waits for the data to be inserted before moving on

                } catch (Exception e) {
                    Log.d(TAG, "getCurrentDayUserInfo: this shouldnt happen");
                }

            }

            currentDayUserInfo = appDatabase.DailyUserInfoModel().getDateLiveData(new Date());
        }
        return currentDayUserInfo;
    }

    public LiveData<List<DailyUserInfoViewModel>> getListOfDailyUserInfo() {
        return listOfDailyUserInfo;
    }

    public void updateCurrentDayUserInfo(DailyUserInfoModel data) {

        new UpdateAsyncTask(appDatabase).execute(data);
    }

    private DailyUserInfoModel retrieveCurrentDayUserInfo() {
        try {
            return new RetrieveAsyncTask(appDatabase).execute().get();

        } catch (Exception e) {
            Log.d(TAG, "RetrieveCurrentDayUserInfo: no value returned due to Exception: " + e);
            return null;

        }
    }

    public List<DailyUserInfoModel> loadBetweenDates(Date from, Date to){
        try {
 
            return new GetAllAsyncTask(appDatabase).execute().get();
        } catch (Exception e) {
            Log.d(TAG, "RetrieveCurrentDayUserInfo: no value returned due to Exception: " + e);
            return null;

        }
    }
    
    // used to insert a new DailyUserInfoModel
     public void insert(DailyUserInfoModel userInfo){
        Log.d(TAG, "insertFood: inserting foodID: " + userInfo.toString());
        new InsertAsyncTask(appDatabase).execute(userInfo);
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

    private static class RetrieveAsyncTask extends AsyncTask<Void, Void, DailyUserInfoModel> {
        private UserNutritionDB db;

        RetrieveAsyncTask(UserNutritionDB appDatabase) {
            db = appDatabase;

        }

        @Override
        protected DailyUserInfoModel doInBackground(Void... params) {
            return db.DailyUserInfoModel().getDate(new Date());

        }
    }

    private static class GetAllAsyncTask extends AsyncTask<Void, Void, List<DailyUserInfoModel>> {
        private UserNutritionDB db;

        GetAllAsyncTask(UserNutritionDB appDatabase) {
            db = appDatabase;

        }

        @Override
        protected List<DailyUserInfoModel> doInBackground(Void... params) {
            return db.DailyUserInfoModel().getAll();
        }
    }

}
