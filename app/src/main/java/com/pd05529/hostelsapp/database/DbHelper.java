package com.pd05529.hostelsapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    private static final String NAME_DATABASE = "hostel_database";
    private static final int VERSION = 1;

    public DbHelper(@Nullable Context context) {
        super(context, NAME_DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create table
        db.execSQL(Data_Sqlite.create_RoomType);
        db.execSQL(Data_Sqlite.create_Room);
        db.execSQL(Data_Sqlite.create_Customer);
        db.execSQL(Data_Sqlite.create_Register);
        db.execSQL(Data_Sqlite.create_Ser);
        db.execSQL(Data_Sqlite.create_Owner);
        db.execSQL(Data_Sqlite.create_Bill);
        db.execSQL(Data_Sqlite.create_register_info);
        //Insert into table
        db.execSQL(Data_Sqlite.insert_owner);
//        db.execSQL(Data_Sqlite.insert_customer);
//        db.execSQL(Data_Sqlite.insert_room_type);
//        db.execSQL(Data_Sqlite.insert_room);
        db.execSQL(Data_Sqlite.insert_service);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists RoomType");
        db.execSQL("drop table if exists Room");
        db.execSQL("drop table if exists Register");
        db.execSQL("drop table if exists Customer");
        db.execSQL("drop table if exists Owner");
        db.execSQL("drop table if exists Service");
        db.execSQL("drop table if exists Bill");
        onCreate(db);
    }
}
