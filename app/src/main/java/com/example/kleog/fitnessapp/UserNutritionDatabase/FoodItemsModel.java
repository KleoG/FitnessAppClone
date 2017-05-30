package com.example.kleog.fitnessapp.UserNutritionDatabase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

/**
 * Created by Kevin on 29/05/2017.
 */

@Entity(tableName = "food_items",
        primaryKeys = {"date", "eaten_during_meal", "food_ID"},
        foreignKeys = @ForeignKey(entity = MealModel.class,
                parentColumns = {"date","meal_type"},
                childColumns = {"date","eaten_during_meal"})
)
public class FoodItemsModel {

    @TypeConverters(DateConverter.class) //used to covert Java date into SQLite compatible data type
    public Date date;

    @ColumnInfo(name = "eaten_during_meal")
    @TypeConverters(MealTypeConverter.class)
    private MealType eatenDuringMeal;

    @ColumnInfo(name = "food_ID")
    private String foodID;

    @ColumnInfo(name = "calories")
    private Integer Calories;

    @ColumnInfo(name = "protein")
    private Integer Protein;

    @ColumnInfo(name = "carbs")
    private Integer Carbs;

    @ColumnInfo(name = "fat")
    private Integer Fat;

    @ColumnInfo(name = "amount_Eaten")
    private Integer amountEaten;

    @ColumnInfo(name = "amount_Eaten_Type") //check enum for types
    @TypeConverters(AmountEatenTypeConverter.class)
    private AmountEatenType amountEatenType;

    public FoodItemsModel(Date date, MealType eatenDuringMeal, String foodID, Integer Calories, Integer Protein, Integer Carbs, Integer Fat, Integer amountEaten, AmountEatenType amountEatenType) {
        this.date = date;
        this.eatenDuringMeal = eatenDuringMeal;
        this.foodID = foodID;
        this.Calories = Calories;
        this.Protein = Protein;
        this.Carbs = Carbs;
        this.Fat = Fat;
        this.amountEaten = amountEaten;
        this.amountEatenType = amountEatenType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public MealType getEatenDuringMeal() {
        return eatenDuringMeal;
    }

    public void setEatenDuringMeal(MealType eatenDuringMeal) {
        this.eatenDuringMeal = eatenDuringMeal;
    }

    public String getFoodID() {
        return foodID;
    }

    public void setFoodID(String foodID) {
        this.foodID = foodID;
    }

    public Integer getCalories() {
        return Calories;
    }

    public void setCalories(Integer calories) {
        Calories = calories;
    }

    public Integer getProtein() {
        return Protein;
    }

    public void setProtein(Integer protein) {
        Protein = protein;
    }

    public Integer getCarbs() {
        return Carbs;
    }

    public void setCarbs(Integer carbs) {
        Carbs = carbs;
    }

    public Integer getFat() {
        return Fat;
    }

    public void setFat(Integer fat) {
        Fat = fat;
    }

    public Integer getAmountEaten() {
        return amountEaten;
    }

    public void setAmountEaten(Integer amountEaten) {
        this.amountEaten = amountEaten;
    }

    public AmountEatenType getAmountEatenType() {
        return amountEatenType;
    }

    public void setAmountEatenType(AmountEatenType amountEatenType) {
        this.amountEatenType = amountEatenType;
    }
}
