package com.example.kleog.fitnessapp.UserNutritionDatabase;

import android.arch.persistence.room.TypeConverter;

/**
 * Created by Kevin on 29/05/2017.
 */

public class MealTypeConverter {
    @TypeConverter
    public static MealType toMealType(String mealType) {
        return mealType == null ? null : MealType.valueOf(mealType);
    }

    @TypeConverter
    public static String toMealTypeString(MealType mealType) {
        return mealType == null ? null : mealType.toString();
    }
}
