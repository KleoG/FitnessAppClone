package com.example.kleog.fitnessapp.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

/**
 * Created by Kevin on 28/05/2017.
 */
//defines class as database table
@Entity(tableName = "Daily_User_Info")
public class DailyUserInfoModel {

    @PrimaryKey()
    @TypeConverters(DateConverter.class) //used to covert Java date into SQLite compatible data type
    public Date date;

    @ColumnInfo(name = "total_Calories")
    private Double totalCalories;

    @ColumnInfo(name = "total_Protein")
    private Double totalProtein;

    @ColumnInfo(name = "total_Carbs")
    private Double totalCarbs;

    @ColumnInfo(name = "total_Fat")
    private Double totalFat;

    @ColumnInfo(name = "weight")
    private Double weight;

    public DailyUserInfoModel(Date date, Double totalCalories, Double totalProtein, Double totalCarbs, Double totalFat, Double weight) {
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

    public Double getWeight() {
        return weight;
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

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String toString(){
        return "Daily user info on date: " + date + " extras: calories: " + totalCalories + ", carbs: " + totalCarbs + ", protein: "+ totalProtein + ", fat: "+ totalFat;
    }
}
