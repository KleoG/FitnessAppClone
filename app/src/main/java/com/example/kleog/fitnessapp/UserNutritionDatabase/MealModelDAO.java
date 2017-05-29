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
@TypeConverters({DateConverter.class, MealTypeConverter.class})
public interface MealModelDAO {
    @Query("SELECT * FROM meal")
    List<MealModel> getAll();

    @Query("SELECT * FROM meal WHERE date = :date and meal_type = :mealType")
    List<MealModel> getMeal(MealType mealType, Date date);

    @Query("SELECT * FROM meal WHERE date = :date")
    List<MealModel> getMealsOnDate(Date date);

    @Insert
    void insertAll(MealModel... meals);

    @Insert(onConflict = REPLACE)
    void insert(MealModel meal);

    @Update
    void update(MealModel meal);

    @Delete
    void delete(MealModel meal);

}