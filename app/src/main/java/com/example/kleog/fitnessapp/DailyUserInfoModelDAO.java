package com.example.kleog.fitnessapp;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;

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

    @Query("SELECT * FROM DailyUserInfoModel WHERE uid IN (:userIds)")
    List<DailyUserInfoModel> loadAllByIds(Date from, Date to);

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND "
            + "last_name LIKE :last LIMIT 1")
    DailyUserInfoModel findByName(String first, String last);

    @Insert
    void insertAll(DailyUserInfoModel... userInfos);

    @Delete
    void delete(DailyUserInfoModel userInfo);

}
