package com.example.kleog.fitnessapp.UserNutritionDatabase;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

/**
 * Created by Kevin on 28/05/2017.
 */
//defines class as database table
@Entity (tableName = "Daily_User_Info")
public class DailyUserInfoModel {

    @PrimaryKey()
    @TypeConverters(DateConverter.class) //used to covert Java date into SQLite compatible data type
    public Date date;

    @ColumnInfo(name = "total_Calories")
    private String totalCalories;

    @ColumnInfo(name = "total_Protein")
    private String totalProtein;

    @ColumnInfo(name = "total_Carbs")
    private String totalCarbs;

    @ColumnInfo(name = "total_Fat")
    private String totalFat;

    @ColumnInfo(name = "weight")
    private String weight;

    public DailyUserInfoModel(Date date, String totalCalories, String totalProtein, String totalCarbs, String totalFat, String weight) {
        this.date = date;
        this.totalCalories = totalCalories;
        this.totalProtein = totalProtein;
        this.totalCarbs = totalCarbs;
        this.totalFat = totalFat;
        this.weight = weight;
    }

    public Date getDate() {
        return date;
    }

    public String getTotalCalories() {
        return totalCalories;
    }

    public String getTotalProtein() {
        return totalProtein;
    }

    public String getTotalCarbs() {
        return totalCarbs;
    }

    public String getTotalFat() {
        return totalFat;
    }

    public String getWeight() {
        return weight;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTotalCalories(String totalCalories) {
        this.totalCalories = totalCalories;
    }

    public void setTotalProtein(String totalProtein) {
        this.totalProtein = totalProtein;
    }

    public void setTotalCarbs(String totalCarbs) {
        this.totalCarbs = totalCarbs;
    }

    public void setTotalFat(String totalFat) {
        this.totalFat = totalFat;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
