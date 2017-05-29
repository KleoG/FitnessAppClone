package com.example.kleog.fitnessapp;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
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
public class mealModel {

    @TypeConverters(DateConverter.class) //used to covert Java date into SQLite compatible data type
    public Date date;

    @ColumnInfo(name = "meal_type")
    private String mealType;

    @ColumnInfo(name = "total_calories")
    private String totalCalories;

    @ColumnInfo(name = "total_protein")
    private String totalProtein;

    @ColumnInfo(name = "total_carbs")
    private String totalCarbs;

    @ColumnInfo(name = "total_fat")
    private String totalFat;

    public mealModel(Date date, String mealType, String totalCalories, String totalProtein, String totalCarbs, String totalFat) {
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

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public String getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(String totalCalories) {
        this.totalCalories = totalCalories;
    }

    public String getTotalProtein() {
        return totalProtein;
    }

    public void setTotalProtein(String totalProtein) {
        this.totalProtein = totalProtein;
    }

    public String getTotalCarbs() {
        return totalCarbs;
    }

    public void setTotalCarbs(String totalCarbs) {
        this.totalCarbs = totalCarbs;
    }

    public String getTotalFat() {
        return totalFat;
    }

    public void setTotalFat(String totalFat) {
        this.totalFat = totalFat;
    }
}