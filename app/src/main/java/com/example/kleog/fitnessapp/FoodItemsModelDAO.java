package com.example.kleog.fitnessapp;

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

    @Query("SELECT * FROM meal WHERE date = :date and meal_type = :mealType")
    List<FoodItemsModel> loadMeal(MealType mealType, Date date);

    @Query("SELECT * FROM meal WHERE date = :date")
    List<FoodItemsModel> getMealsOnDate(Date date);

    @Insert
    void insertAll(FoodItemsModel... meals);

    @Insert(onConflict = REPLACE)
    void insert(FoodItemsModel... foodItems);

    @Update
    void update(FoodItemsModel foodItems);

    @Delete
    void delete(FoodItemsModel foodItems);

}