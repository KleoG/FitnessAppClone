package com.example.kleog.fitnessapp;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

import java.util.Date;
import java.util.List;

/**
 * Created by Kevin on 29/05/2017.
 */

@Dao
@TypeConverters(DateConverter.class)
public interface DailyUserInfoModelDAO {
    @Query("SELECT * FROM DailyUserInfoModel")
    List<DailyUserInfoModel> getAll();

    @Query("SELECT * FROM DailyUserInfoModel WHERE date BETWEEN :from AND :to")
    List<DailyUserInfoModel> loadBetweenDates(Date from, Date to);

    @Query("SELECT * FROM DailyUserInfoModel WHERE date = :date")
    DailyUserInfoModel getDate(Date date);

    @Insert
    void insertAll(DailyUserInfoModel... userInfos);

    @Insert(onConflict = REPLACE)
    void insert(DailyUserInfoModel userInfo);

    @Delete
    void delete(DailyUserInfoModel userInfo);

}
