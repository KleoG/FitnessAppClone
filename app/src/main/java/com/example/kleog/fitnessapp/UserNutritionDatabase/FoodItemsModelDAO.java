package com.example.kleog.fitnessapp.UserNutritionDatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import java.util.Date;
import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by Kevin on 29/05/2017.
 */

@Dao
@TypeConverters({DateConverter.class, MealTypeConverter.class, AmountEatenTypeConverter.class})
public interface FoodItemsModelDAO {
    @Query("SELECT * FROM food_items")
    List<FoodItemsModel> getAll();

    @Query("SELECT * FROM food_items WHERE date = :date")
    List<FoodItemsModel> getFoodsEatenOnDate(Date date);

    @Query("SELECT * FROM food_items WHERE date = :date AND meal_type = :mealType")
    List<FoodItemsModel> getFoodEatenOnDateAndMealType(Date date,MealType mealType);

    @Query("SELECT * FROM WHERE date = :date AND meal_type = :mealType AND food_ID = foodID")
    FoodItemsModel getsingleFoodItem(Date date,MealType mealType, String foodID);

    @Insert
    void insertAll(FoodItemsModel... foodItems);

    @Insert(onConflict = REPLACE)
    void insert(FoodItemsModel foodItem);

    @Update
    void update(FoodItemsModel foodItem);

    @Delete
    void delete(FoodItemsModel foodItem);

}