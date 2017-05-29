package com.example.kleog.fitnessapp;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

/**
 * Created by Kevin on 29/05/2017.
 */

@Entity(tableName = "food_items",
        primaryKeys = {"date", "meal_type"},
        foreignKeys = @ForeignKey(entity = DailyUserInfoModel.class,
                parentColumns = "date",
                childColumns = "date")
)
public class FoodItemsModel {

    @TypeConverters(DateConverter.class) //used to covert Java date into SQLite compatible data type
    public Date date;

    @ColumnInfo(name = "eaten_during_meal")
    private MealType eatenDuringMeal;

    private String foodID;
    private String Calories;
    private String Protein;
    private String Carbs;
    private String Fat;
    private Integer amountEaten;
}
