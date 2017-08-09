package com.example.kleog.fitnessapp.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

/**
 * Created by Kevin on 29/05/2017.
 */


@Entity(tableName = "meal",
        primaryKeys = {"date", "meal_type"},
        foreignKeys = @ForeignKey(entity = DailyUserInfoModel.class,
                parentColumns = "date",
                childColumns = "date")
)
public class MealModel {

    @TypeConverters(DateConverter.class) //used to covert Java date into SQLite compatible data type
    public Date date;

    @ColumnInfo(name = "meal_type")
    @TypeConverters(MealTypeConverter.class)
    private MealType mealType;

    @ColumnInfo(name = "total_calories")
    private Double totalCalories;

    @ColumnInfo(name = "total_protein")
    private Double totalProtein;

    @ColumnInfo(name = "total_carbs")
    private Double totalCarbs;

    @ColumnInfo(name = "total_fat")
    private Double totalFat;

    public MealModel(Date date, MealType mealType, Double totalCalories, Double totalProtein, Double totalCarbs, Double totalFat) {
        this.date = date;
        this.mealType = mealType;
        this.totalCalories = totalCalories;
        this.totalProtein = totalProtein;
        this.totalCarbs = totalCarbs;
        this.totalFat = totalFat;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public MealType getMealType() {
        return mealType;
    }

    public Double getTotalCalories() {
        return totalCalories;
    }

    public Double getTotalProtein() {
        return totalProtein;
    }

    public Double getTotalCarbs() {
        return totalCarbs;
    }

    public Double getTotalFat() {
        return totalFat;
    }

    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }

    public void setTotalCalories(Double totalCalories) {
        this.totalCalories = totalCalories;
    }

    public void setTotalProtein(Double totalProtein) {
        this.totalProtein = totalProtein;
    }

    public void setTotalCarbs(Double totalCarbs) {
        this.totalCarbs = totalCarbs;
    }

    public void setTotalFat(Double totalFat) {
        this.totalFat = totalFat;
    }
}