package com.example.kleog.fitnessapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.kleog.fitnessapp.UserNutritionDatabase.DailyUserInfoModel;
import com.example.kleog.fitnessapp.UserNutritionDatabase.UserNutritionDB;

import java.util.Date;
import java.util.List;

/**
 * Created by Kevin on 17/07/2017.
 */

public class DailyUserInfoViewModel extends AndroidViewModel {

    private LiveData<DailyUserInfoModel> currentDayUserInfo;

    private LiveData<List<DailyUserInfoViewModel>> listOfDailyUserInfo;

    private final UserNutritionDB appDatabase;

    public DailyUserInfoViewModel(Application application) {
        super(application);

        appDatabase = UserNutritionDB.getDatabase(this.getApplication());

        currentDayUserInfo = getCurrentDayUserInfo();

        listOfDailyUserInfo = null;
    }

    public LiveData<DailyUserInfoModel> getCurrentDayUserInfo() {

        if(currentDayUserInfo == null){

            currentDayUserInfo = appDatabase.DailyUserInfoModel().getDate(new Date());

            //if new day and no data inserted yet then create and add to database
            if (currentDayUserInfo.getValue() == null){

                DailyUserInfoModel emptyCurrentDay = new DailyUserInfoModel(new Date(), 0, 0, 0, 0, 0);

                new InsertAsyncTask(appDatabase).execute(emptyCurrentDay);
            }
        }
        return currentDayUserInfo;
    }

    //private static class created for inserting into database with async task to prevent memory leaks
    private static class InsertAsyncTask extends AsyncTask<DailyUserInfoModel, Void, Void> {

        private UserNutritionDB db;

        InsertAsyncTask(UserNutritionDB appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(DailyUserInfoModel ... params ) {
            db.DailyUserInfoModel().insert(params[0]);

            return null;
        }

    }

    public LiveData<List<DailyUserInfoViewModel>> getListOfDailyUserInfo() {
        return listOfDailyUserInfo;
    }

    public void updateCurrentDayUserInfo(DailyUserInfoModel data){

        new UpdateAsyncTask(appDatabase).execute(data);
        //currentDayUserInfo = appDatabase.DailyUserInfoModel().getDate(new Date());
    }

    //private static class created for updating database with async task to prevent memory leaks
    private static class UpdateAsyncTask extends AsyncTask<DailyUserInfoModel, Void, Void> {

        private UserNutritionDB db;

        UpdateAsyncTask(UserNutritionDB appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(DailyUserInfoModel ... params ) {
            db.DailyUserInfoModel().update(params[0]);

            return null;
        }

    }




}
