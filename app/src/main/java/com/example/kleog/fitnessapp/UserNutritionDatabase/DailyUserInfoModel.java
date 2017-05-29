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
    private Integer totalCalories;

    @ColumnInfo(name = "total_Protein")
    private Integer totalProtein;

    @ColumnInfo(name = "total_Carbs")
    private Integer totalCarbs;

    @ColumnInfo(name = "total_Fat")
    private Integer totalFat;

    @ColumnInfo(name = "weight")
    private Integer weight;

    public DailyUserInfoModel(Date date, Integer totalCalories, Integer totalProtein, Integer totalCarbs, Integer totalFat, Integer weight) {
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

    public Integer getTotalCalories() {
        return totalCalories;
    }

    public Integer getTotalProtein() {
        return totalProtein;
    }

    public Integer getTotalCarbs() {
        return totalCarbs;
    }

    public Integer getTotalFat() {
        return totalFat;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTotalCalories(Integer totalCalories) {
        this.totalCalories = totalCalories;
    }

    public void setTotalProtein(Integer totalProtein) {
        this.totalProtein = totalProtein;
    }

    public void setTotalCarbs(Integer totalCarbs) {
        this.totalCarbs = totalCarbs;
    }

    public void setTotalFat(Integer totalFat) {
        this.totalFat = totalFat;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
