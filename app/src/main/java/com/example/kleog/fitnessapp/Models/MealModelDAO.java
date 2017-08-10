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
@TypeConverters({DateConverter.class, MealTypeConverter.class})
public interface MealModelDAO {
    @Query("SELECT * FROM meal")
    LiveData<List<MealModel>> getAllLiveData();

    /**
     * @param mealType
     * @param date
     * @return live data meal model
     */
    @Query("SELECT * FROM meal WHERE date = :date and meal_type = :mealType")
    LiveData<MealModel> getMealLiveData(MealType mealType, Date date);

    /**
     * @param mealType
     * @param date
     * @return meal model object (not live data)
     */
    @Query("SELECT * FROM meal WHERE date = :date and meal_type = :mealType")
    MealModel getMeal(MealType mealType, Date date);

    @Query("SELECT * FROM meal WHERE date = :date")
    LiveData<List<MealModel>> getMealsOnDateLiveData(Date date);

    @Insert(onConflict = IGNORE)
    void insertAll(MealModel... meals);

    @Insert(onConflict = IGNORE)
    void insert(MealModel meal);

    @Update
    void update(MealModel meal);

    @Delete
    void delete(MealModel meal);

}