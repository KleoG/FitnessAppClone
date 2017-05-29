package com.example.kleog.fitnessapp;

import android.arch.persistence.room.TypeConverter;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kevin on 29/05/2017.
 */

public class MealTypeConverter {
    @TypeConverter
    public static MealType toMealType(String mealType) {
        return mealType == null ? null : MealType.valueOf(mealType);
    }

    @TypeConverter
    public static String toMealTypeString (MealType mealType) {
        return mealType == null ? null : mealType.toString();
    }
}
