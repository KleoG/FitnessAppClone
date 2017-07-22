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
                parentColumns = {"date", "meal_type"},
                childColumns = {"date", "eaten_during_meal"})
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
    private Double Calories;

    @ColumnInfo(name = "protein")
    private Double Protein;

    @ColumnInfo(name = "carbs")
    private Double Carbs;

    @ColumnInfo(name = "fat")
    private Double Fat;

    @ColumnInfo(name = "amount_Eaten")
    private Double amountEaten;

    @ColumnInfo(name = "amount_Eaten_Type") //check enum for types
    @TypeConverters(AmountEatenTypeConverter.class)
    private AmountEatenType amountEatenType;

    public FoodItemsModel(Date date, MealType eatenDuringMeal, String foodID, Double Calories, Double Protein, Double Carbs, Double Fat, Double amountEaten, AmountEatenType amountEatenType) {
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

    public Double getCalories() {
        return Calories;
    }

    public Double getProtein() {
        return Protein;
    }

    public Double getCarbs() {
        return Carbs;
    }

    public Double getFat() {
        return Fat;
    }

    public Double getAmountEaten() {
        return amountEaten;
    }

    public AmountEatenType getAmountEatenType() {
        return amountEatenType;
    }

}
