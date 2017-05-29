package com.example.kleog.fitnessapp;

import android.arch.persistence.room.TypeConverter;

/**
 * Created by Kevin on 29/05/2017.
 */

public class AmountEatenTypeConverter {
    @TypeConverter
    public static AmountEatenType toAmountEatenType(String amountEatenType) {
        return amountEatenType == null ? null : AmountEatenType.valueOf(amountEatenType);
    }

    @TypeConverter
    public static String toAmountEatenTypeString (MealType amountEatenType) {
        return amountEatenType == null ? null : amountEatenType.toString();
    }
}
