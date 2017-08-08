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
@TypeConverters(DateConverter.class)
public interface DailyUserInfoModelDAO {
    @Query("SELECT * FROM Daily_User_Info")
    LiveData<List<DailyUserInfoModel>> getAll();

    @Query("SELECT * FROM Daily_User_Info WHERE date BETWEEN :from AND :to")
    LiveData<List<DailyUserInfoModel>> loadBetweenDates(Date from, Date to);

    @Query("SELECT * FROM Daily_User_Info WHERE date = :date")
    LiveData<DailyUserInfoModel> getDate(Date date);

    @Insert
    void insertAll(DailyUserInfoModel... userInfos);

    @Insert(onConflict = IGNORE)
    void insert(DailyUserInfoModel userInfo);

    @Update
    void update(DailyUserInfoModel userInfo);

    @Delete
    void delete(DailyUserInfoModel userInfo);

}
