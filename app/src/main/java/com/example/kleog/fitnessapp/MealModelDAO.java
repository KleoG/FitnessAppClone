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
@TypeConverters(DateConverter.class)
public interface MealModelDAO {
    @Query("SELECT * FROM meal")
    List<MealModel> getAll();

    @Query("SELECT * FROM meal WHERE date = :date and meal_type = :mealType")
    List<MealModel> loadMeal(MealType mealType, Date date);

    @Query("SELECT * FROM meal WHERE date = :date")
    List<MealModel> getMealsOnDate(Date date);

    @Insert
    void insertAll(MealModelDAO... meals);

    @Insert(onConflict = REPLACE)
    void insert(MealModelDAO meal);

    @Update
    void update(MealModelDAO meal);

    @Delete
    void delete(MealModelDAO meal);

}