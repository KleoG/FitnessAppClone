package com.example.kleog.fitnessapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Kevin on 22/05/2017.
 */

public class UserNutritionDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "User Nutrition";


    //TABLE: daily user info
    private static final String DAILY_USER_INFO_TABLE_NAME = "daily user info";
    //columns
    private static final String DAILY_USER_INFO_COLUMN_DATE = "date";
    private static final String DAILY_USER_INFO_COLUMN_TOTAL_CALORIES = "totalCalories";
    private static final String DAILY_USER_INFO_COLUMN_TOTAL_PROTEIN = "totalProtein";
    private static final String DAILY_USER_INFO_COLUMN_TOTAL_CARBS = "totalCarbs";
    private static final String DAILY_USER_INFO_COLUMN_TOTAL_FAT = "totalFat";
    private static final String DAILY_USER_INFO_COLUMN_WEIGHT = "weight";
    //creation of daily user info table
    private static final String DAILY_USER_INFO_TABLE_CREATE =
            "CREATE TABLE " + DAILY_USER_INFO_TABLE_NAME + " (" +
                    DAILY_USER_INFO_COLUMN_DATE + " date primary key, " +

                    DAILY_USER_INFO_COLUMN_TOTAL_CALORIES + " integer, " +

                    DAILY_USER_INFO_COLUMN_TOTAL_PROTEIN + " integer, " +

                    DAILY_USER_INFO_COLUMN_TOTAL_CARBS + " integer, " +

                    DAILY_USER_INFO_COLUMN_TOTAL_FAT + " integer, " +

                    DAILY_USER_INFO_COLUMN_WEIGHT + " real);";

    UserNutritionDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //.execSQL() can only create one table at a time. to have multiple tables use method multiple times
        db.execSQL(DAILY_USER_INFO_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


}
