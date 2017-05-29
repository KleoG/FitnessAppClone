package com.example.kleog.fitnessapp.UserNutritionDatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

import java.util.Date;
import java.util.List;

/**
 * Created by Kevin on 29/05/2017.
 */

@Dao
@TypeConverters(DateConverter.class)
public interface DailyUserInfoModelDAO {
    @Query("SELECT * FROM Daily_User_Info")
    List<DailyUserInfoModel> getAll();

    @Query("SELECT * FROM Daily_User_Info WHERE date BETWEEN :from AND :to")
    List<DailyUserInfoModel> loadBetweenDates(Date from, Date to);

    @Query("SELECT * FROM Daily_User_Info WHERE date = :date")
    DailyUserInfoModel getDate(Date date);

    @Insert
    void insertAll(DailyUserInfoModel... userInfos);

    @Insert(onConflict = REPLACE)
    void insert(DailyUserInfoModel userInfo);

    @Update
    void update(DailyUserInfoModel userInfo);

    @Delete
    void delete(DailyUserInfoModel userInfo);

}
