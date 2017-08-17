package com.example.kleog.fitnessapp.Models;

import android.arch.persistence.room.TypeConverter;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kevin on 28/05/2017.
 */

public class DateConverter {

    @TypeConverter
    static Date toDate(String strDate) {
        try {
            return strDate == null ? null : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strDate); //takes a string date from SQLite and converts it to Date in format
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d("DATE_CONVERTER", "error converting: " + strDate + " , to a Date data type");
        return null;
    }

    @TypeConverter
    static String toDateString(Date date) {
        Log.d("DATE_CONVERTER", "toDateString: " + date + " becomes: " + new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(date));
        return date == null ? null : new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(date);
    }
}
