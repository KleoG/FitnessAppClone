package com.example.kleog.fitnessapp;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

/**
 * Created by Kevin on 28/05/2017.
 */

@Entity //defines class as database table
public class DailyUserInfoModel {

    @PrimaryKey()
    @TypeConverters(DateConverter.class) //used to covert Java date into SQLite compatible data type
    public Date date;
    private String totalCalories;
    private String totalProtein;
    private String totalCarbs;
    private String totalFat;
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
}
