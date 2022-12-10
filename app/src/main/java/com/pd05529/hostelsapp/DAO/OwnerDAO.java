package com.pd05529.hostelsapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.pd05529.hostelsapp.database.DbHelper;
import com.pd05529.hostelsapp.models.Owner;
import com.pd05529.hostelsapp.support.Converter;

import java.util.ArrayList;
import java.util.List;

public class OwnerDAO {
    private SQLiteDatabase db;

    public OwnerDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        this.db = dbHelper.getWritableDatabase();
    }

    //    //Insert
    public long insert(Owner obj){
        ContentValues ct = new ContentValues();
        ct.put("name",obj.getName());
        ct.put("username",obj.getUsername());
        ct.put("password", obj.getPassword());
        ct.put("phone", obj.getPhone());
        ct.put("email",obj.getEmail());
        ct.put("address",obj.getAddress());
        ct.put("gender",obj.getGender());
        ct.put("cmnd",obj.getCmnd());
        ct.put("date", Converter.toTimestamp(obj.getDate()));
        return db.insert("Owner",null,ct);
    }
    //Update
    public int update(Owner obj) {
        ContentValues ct = new ContentValues();
        ct.put("name", obj.getName());
        ct.put("username", obj.getUsername());
        ct.put("password", obj.getPassword());
        ct.put("email", obj.getEmail());
        ct.put("phone", obj.getPhone());
        ct.put("address", obj.getAddress());
        ct.put("gender", obj.getGender());
        ct.put("cmnd", obj.getCmnd());
        ct.put("date", Converter.toTimestamp(obj.getDate()));
        if (db.update("Owner", ct, "idOwner=?", new String[]{String.valueOf(obj.getIdOwner())}) == -1) {
            return -1;
        }
        return 1;
    }

        //Delete
    public int delete(String id){
        return db.delete("Owner","idOwner=?",new String[]{String.valueOf(id)});
    }
    //get All
    public List<Owner> getAll(){
        String sql = "SELECT * FROM Owner";
        return getData(sql);
    }
    //get data
    public List<Owner> getData(String sql, String... selection) {
        List<Owner> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selection);
        while (cursor.moveToNext()) {
            Owner obj = new Owner(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getInt(7),
                    cursor.getInt(8),
                    Converter.toDate(cursor.getString(9)));
            list.add(obj);
        }
        return list;
    }
    public Owner getId(String id) {
        String sql = "SELECT * FROM Owner WHERE idOwner=?";
        List<Owner> typeList = getData(sql, id);
        if (typeList.isEmpty()) {
            return null;
        }
        return typeList.get(0);
    }
    //Check login
    public Owner checkLogin(String id, String pass) {
        String sql = "Select * from Owner WHERE username=? AND password=?";
        List<Owner> list = getData(sql, id, pass);
        if (list.size() == 0) {
            return null;
        }
        return list.get(0);
    }
}
