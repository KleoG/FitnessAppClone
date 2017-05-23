package com.example.kleog.fitnessapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Kevin on 22/05/2017.
 */

public class UserNutritionDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "UserNutrition.db";


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

    //-------------------------------------------------------------------------------------------------------

    //TABLE: meal
    private static final String MEAL_TABLE_NAME = "meal";
    //columns
    private static final String MEAL_COLUMN_DATE = "date";
    private static final String MEAL_COLUMN_MEAL_TYPE = "mealType";
    private static final String MEAL_COLUMN_TOTAL_CALORIES = "totalCalories";
    private static final String MEAL_COLUMN_TOTAL_PROTEIN = "totalProtein";
    private static final String MEAL_COLUMN_TOTAL_CARBS = "totalCarbs";
    private static final String MEAL_COLUMN_TOTAL_FAT = "totalFat";

    //creation of meal table
    private static final String MEAL_TABLE_CREATE =
            "CREATE TABLE " + MEAL_TABLE_NAME + " (" +
                    MEAL_COLUMN_DATE + " date primary key , " +

                    MEAL_COLUMN_MEAL_TYPE + " text primary key, " +

                    MEAL_COLUMN_TOTAL_CALORIES + " integer, " +

                    MEAL_COLUMN_TOTAL_PROTEIN + " integer, " +

                    MEAL_COLUMN_TOTAL_CARBS + " integer, " +

                    MEAL_COLUMN_TOTAL_FAT + " integer, " +

                    " FOREIGN KEY (" + MEAL_COLUMN_MEAL_TYPE + ") REFERENCES "+ DAILY_USER_INFO_TABLE_NAME +"(" + DAILY_USER_INFO_COLUMN_DATE + ") );";


    //----------------------------------------------------------------------------------------------------------------------------------------

    //TABLE: food items
    private static final String FOOD_ITEMS_TABLE_NAME = "food items";
    //columns
    private static final String FOOD_ITEMS_COLUMN_DATE = "date";
    private static final String FOOD_ITEMS_COLUMN_TIME_EATEN = "timeEaten";
    private static final String FOOD_ITEMS_COLUMN_FOOD_ID = "foodID";
    private static final String FOOD_ITEMS_COLUMN_CALORIES = "Calories";
    private static final String FOOD_ITEMS_COLUMN_PROTEIN = "Protein";
    private static final String FOOD_ITEMS_COLUMN_CARBS = "Carbs";
    private static final String FOOD_ITEMS_COLUMN_FAT = "Fat";
    private static final String FOOD_ITEMS_COLUMN_AMOUNT_EATEN = "amountEaten";


    //creation of food items table
    private static final String FOOD_ITEMS_TABLE_CREATE =
            "CREATE TABLE " + FOOD_ITEMS_TABLE_NAME + " (" +
                    FOOD_ITEMS_COLUMN_DATE + " date primary key , " +

                    FOOD_ITEMS_COLUMN_TIME_EATEN + " text primary key, " +

                    FOOD_ITEMS_COLUMN_FOOD_ID + " integer primary key, " +

                    FOOD_ITEMS_COLUMN_CALORIES + " integer, " +

                    FOOD_ITEMS_COLUMN_PROTEIN + " integer, " +

                    FOOD_ITEMS_COLUMN_CARBS + " integer, " +

                    FOOD_ITEMS_COLUMN_FAT + " integer, " +

                    FOOD_ITEMS_COLUMN_AMOUNT_EATEN + " integer, " +

                    " FOREIGN KEY (" + FOOD_ITEMS_COLUMN_DATE + ") REFERENCES "+ MEAL_TABLE_NAME +"(" + MEAL_COLUMN_DATE + ")" +

                    " FOREIGN KEY (" + FOOD_ITEMS_COLUMN_TIME_EATEN + ") REFERENCES "+ MEAL_TABLE_NAME +"(" + MEAL_COLUMN_MEAL_TYPE + ") );";



    UserNutritionDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //.execSQL() can only create one table at a time. to have multiple tables use method multiple times
        db.execSQL(DAILY_USER_INFO_TABLE_CREATE);
        db.execSQL(MEAL_TABLE_CREATE);
        db.execSQL(FOOD_ITEMS_TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //used to modify previous table when upgrading databse
        //db.execSQL(" drop table if exists "+ EXAMPLE_TABLE_NAME);


        //do not touch
        onCreate(db);

    }


}
