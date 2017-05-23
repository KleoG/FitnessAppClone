package com.example.kleog.fitnessapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Kevin on 22/05/2017.
 */

public class UserNutritionDBHelper extends SQLiteOpenHelper {
    //singleton class contains instance of the database
    private static UserNutritionDBHelper DBInstance;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "UserNutrition.db";

    //tables separated using lines for readability
    //--------------------------------------------------------------------------------------------------------

    //TABLE: daily user info
    private static final String DAILY_USER_INFO_TABLE_NAME = "daily_user_info";
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
                    DAILY_USER_INFO_COLUMN_DATE + " TEXT PRIMARY KEY, " +

                    DAILY_USER_INFO_COLUMN_TOTAL_CALORIES + " INTEGER, " +

                    DAILY_USER_INFO_COLUMN_TOTAL_PROTEIN + " INTEGER, " +

                    DAILY_USER_INFO_COLUMN_TOTAL_CARBS + " INTEGER, " +

                    DAILY_USER_INFO_COLUMN_TOTAL_FAT + " INTEGER, " +

                    DAILY_USER_INFO_COLUMN_WEIGHT + " INTEGER);";

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
                    MEAL_COLUMN_DATE + " TEXT PRIMARY KEY , " +

                    MEAL_COLUMN_MEAL_TYPE + " TEXT PRIMARY KEY, " +

                    MEAL_COLUMN_TOTAL_CALORIES + " INTEGER, " +

                    MEAL_COLUMN_TOTAL_PROTEIN + " INTEGER, " +

                    MEAL_COLUMN_TOTAL_CARBS + " INTEGER, " +

                    MEAL_COLUMN_TOTAL_FAT + " INTEGER, " +

                    " FOREIGN KEY (" + MEAL_COLUMN_MEAL_TYPE + ") REFERENCES "+ DAILY_USER_INFO_TABLE_NAME +"(" + DAILY_USER_INFO_COLUMN_DATE + ") );";


    //----------------------------------------------------------------------------------------------------------------------------------------

    //TABLE: food items
    private static final String FOOD_ITEMS_TABLE_NAME = "food_items";
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
                    FOOD_ITEMS_COLUMN_DATE + " TEXT PRIMARY KEY , " +

                    FOOD_ITEMS_COLUMN_TIME_EATEN + " TEXT PRIMARY KEY, " +

                    FOOD_ITEMS_COLUMN_FOOD_ID + " INTEGER PRIMARY KEY, " +

                    FOOD_ITEMS_COLUMN_CALORIES + " INTEGER, " +

                    FOOD_ITEMS_COLUMN_PROTEIN + " INTEGER, " +

                    FOOD_ITEMS_COLUMN_CARBS + " INTEGER, " +

                    FOOD_ITEMS_COLUMN_FAT + " INTEGER, " +

                    FOOD_ITEMS_COLUMN_AMOUNT_EATEN + " INTEGER, " +

                    " FOREIGN KEY (" + FOOD_ITEMS_COLUMN_DATE + ") REFERENCES "+ MEAL_TABLE_NAME +"(" + MEAL_COLUMN_DATE + ")," +

                    " FOREIGN KEY (" + FOOD_ITEMS_COLUMN_TIME_EATEN + ") REFERENCES "+ MEAL_TABLE_NAME +"(" + MEAL_COLUMN_MEAL_TYPE + ") );";





    //use this to get access to the database
    public static synchronized UserNutritionDBHelper getInstance(Context context) {

        if (DBInstance == null) {
            DBInstance = new UserNutritionDBHelper(context.getApplicationContext());
        }
        return DBInstance;
    }

    //kept private to not have multiple instances of the database
    private UserNutritionDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

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
        //used to modify previous table when upgrading database
        //db.execSQL(" drop table if exists "+ EXAMPLE_TABLE_NAME);


        //do not touch
        onCreate(db);

    }




}
