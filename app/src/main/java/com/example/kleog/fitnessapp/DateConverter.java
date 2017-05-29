package com.example.kleog.fitnessapp;
import android.arch.persistence.room.TypeConverter;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kevin on 28/05/2017.
 */

class DateConverter {

    @TypeConverter
    public static Date toDate(String strDate) {
        try {
            return strDate == null ? null :  new SimpleDateFormat("yyyyMMddHHmmss").parse(strDate + "000000"); //takes a string date from SQLite and converts it to Date in format
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d("DATE_CONVERTER", "error converting: " + strDate + " , to a Date data type");
        return null;
    }

    @TypeConverter
    public static String toDateString (Date date) {
        Log.d("DATE_CONVERTER", "toDateString: " + date + " becomes: " + new SimpleDateFormat("YYYY-MM-dd").format(new Date()).toString());
        return date == null ? null :  new SimpleDateFormat("YYYY-MM-dd").format(new Date()).toString();
    }
}
