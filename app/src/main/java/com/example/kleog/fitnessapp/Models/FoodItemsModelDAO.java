package com.example.kleog.fitnessapp.Models;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import java.util.Date;
import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

/**
 * Created by Kevin on 29/05/2017.
 */

@Dao
@TypeConverters({DateConverter.class, MealTypeConverter.class, AmountEatenTypeConverter.class})
public interface FoodItemsModelDAO {
    @Query("SELECT * FROM food_items")
    List<FoodItemsModel> getAll();

    @Query("SELECT * FROM food_items WHERE date = :date")
    LiveData<List<FoodItemsModel>> getFoodsEatenOnDate(Date date);

    @Query("SELECT * FROM food_items WHERE date = :date AND eaten_during_meal = :mealType")
    LiveData<List<FoodItemsModel>> getFoodEatenOnDateAndMealType(Date date, MealType mealType);

    @Query("SELECT * FROM food_items WHERE date = :date AND eaten_during_meal = :mealType AND food_ID = :foodID")
    FoodItemsModel getSingleFoodItem(Date date, MealType mealType, long foodID);

    @Insert
    void insertAll(FoodItemsModel... foodItems);

    @Insert(onConflict = IGNORE)
    void insert(FoodItemsModel foodItem);

    @Update
    void update(FoodItemsModel foodItem);

    @Delete
    void delete(FoodItemsModel foodItem);

}