package com.pd05529.hostelsapp.support;

import android.annotation.SuppressLint;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Converter {
    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

    public static Date toDate(String stringDate) {
        try {
            return format.parse(stringDate);
        } catch (Exception e) {
            return null;
        }
    }

    public static String toTimestamp(Date date) {
        try {
            return format.format(date);
        } catch (NullPointerException e) {
            return null;
        }
    }

    private static final DecimalFormat f = new DecimalFormat ("###,###,###,###");

    public static String toStr(long i){
        try {
            return f.format(i);
        }catch (NullPointerException e){
            return null;
        }
    }
}










