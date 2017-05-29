package com.example.kleog.fitnessapp;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by Kevin on 29/05/2017.
 */


@Database(entities = {DailyUserInfoModel.class}, version = 1)
public abstract class UserNutritionDB extends RoomDatabase {

    private static final String DATABASE_NAME = "user_nutrition_db";

    private static UserNutritionDB INSTANCE;

    //use this method to get an instance of the database
    public static UserNutritionDB getDatabase(Context context) {
        if (INSTANCE == null) {
            //builds database
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), UserNutritionDB.class, DATABASE_NAME).build();
        }
        return INSTANCE;
    }

    //create abstract class for each DAO
    public abstract DailyUserInfoModelDAO DailyUserInfoModel();

}