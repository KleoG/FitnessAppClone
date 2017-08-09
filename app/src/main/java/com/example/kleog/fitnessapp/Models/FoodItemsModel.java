package com.example.kleog.fitnessapp.Models;

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
    private Date date;

    @ColumnInfo(name = "eaten_during_meal")
    @TypeConverters(MealTypeConverter.class)
    private MealType eatenDuringMeal;

    @ColumnInfo(name = "food_ID")
    private long foodID;

    private Double calories;

    private Double protein;

    private Double carbs;

    private Double fat;

//    /**
//     * standardised units are only grams, ounces and ml for foods
//     */
//    @ColumnInfo(name = "standardised_amount_eaten")
//    private Double standardisedAmountEaten;
//
//    @ColumnInfo(name = "standardised_amount_eaten_type") //check enum for types
//    @TypeConverters(AmountEatenTypeConverter.class)
//    private AmountEatenType standardisedAmountEatenType;

    /**
     * stores index of the serving chosen by user
     */
    @ColumnInfo(name = "API_serving_chosen")
    private int servingChosen;

    @ColumnInfo(name = "Serving_units_chosen")
    private Double servingUnits;

    @ColumnInfo(name = "food_description")
    private String foodDescription;

    //TODO add food description

    public FoodItemsModel(Date date, MealType eatenDuringMeal, long foodID, Double calories, Double protein, Double carbs, Double fat, int servingChosen, Double servingUnits, String foodDescription) {
        this.date = date;
        this.eatenDuringMeal = eatenDuringMeal;
        this.foodID = foodID;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
        this.servingChosen = servingChosen;
        this.servingUnits = servingUnits;
        this.foodDescription = foodDescription;
    }

    public Date getDate() {
        return date;
    }

    public MealType getEatenDuringMeal() {
        return eatenDuringMeal;
    }

    public long getFoodID() {
        return foodID;
    }

    public Double getCalories() {
        return calories;
    }

    public Double getProtein() {
        return protein;
    }

    public Double getCarbs() {
        return carbs;
    }

    public Double getFat() {
        return fat;
    }

//    public Double getStandardisedAmountEaten() {
//        return standardisedAmountEaten;
//    }
//
//    public AmountEatenType getStandardisedAmountEatenType() {
//        return standardisedAmountEatenType;
//    }

    public int getServingChosen() {
        return servingChosen;
    }

    public Double getServingUnits() {
        return servingUnits;
    }

    public String getFoodDescription() {
        return foodDescription;
    }
}
