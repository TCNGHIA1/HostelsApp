package com.pd05529.hostelsapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.pd05529.hostelsapp.database.DbHelper;
import com.pd05529.hostelsapp.models.RoomType;

import java.util.ArrayList;
import java.util.List;

public class RoomTypeDAO {
    private SQLiteDatabase db;

    public RoomTypeDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        this.db = dbHelper.getWritableDatabase();
    }

    //Insert
    public long insert(RoomType obj) {
        ContentValues ct = new ContentValues();
        ct.put("name", obj.getName());
        ct.put("price", obj.getPrice());
        ct.put("squareArea", obj.getSquareArea());
        ct.put("maxMember", obj.getMaxMember());
        if (db.insert("RoomType", null, ct) == -1) {
            return -1;
        }
        return 1;
    }

    //Update
    public long update(RoomType obj) {
        ContentValues ct = new ContentValues();
        ct.put("name", obj.getName());
        ct.put("price", obj.getPrice());
        ct.put("squareArea", obj.getSquareArea());
        ct.put("maxMember", obj.getMaxMember());
        if (db.update("RoomType", ct, "idType=?", new String[]{String.valueOf(obj.getIdType())}) == -1) {
            return -1;
        }
        return 1;
    }

    //Delete
    public int delete(String id) {
        return db.delete("RoomType", "idType=?", new String[]{id});
    }

    public void deleteAll() {
        db.execSQL("DELETE FROM RoomType");
    }

    //Show all
    public List<RoomType> getAll() {
        String sql = "SELECT * FROM RoomType";
        return getData(sql);
    }

    //get Data
    public List<RoomType> getData(String sql, String... select) {
        List<RoomType> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, select);
        while (cursor.moveToNext()) {
            RoomType obj = new RoomType(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getLong(2),
                    cursor.getInt(3),
                    cursor.getInt(4)
            );
            list.add(obj);
        }
        return list;
    }

    public RoomType getId(String id) {
        String sql = "SELECT * FROM RoomType WHERE idType=?";
        List<RoomType> typeList = getData(sql, id);
        if (typeList.isEmpty()) {
            return null;
        }
        return typeList.get(0);
    }
}
