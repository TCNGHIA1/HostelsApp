package com.pd05529.hostelsapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.pd05529.hostelsapp.database.DbHelper;
import com.pd05529.hostelsapp.models.Room;

import java.util.ArrayList;
import java.util.List;

public class RoomDAO {
    private SQLiteDatabase db;

    public RoomDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        this.db = dbHelper.getWritableDatabase();
    }

    //Insert
    public long insert(Room obj) {
        ContentValues ct = new ContentValues();
        ct.put("idRoom", obj.getIdRoom().toUpperCase());
        ct.put("name", obj.getName());
        ct.put("idType", obj.getIdType());
        ct.put("mode", obj.getMode());
        ct.put("note", obj.getNote());
        if (db.insert("Room", null, ct) == -1) {
            return -1;
        }
        return 1;
    }

    //Update
    public long update(Room obj) {
        ContentValues ct = new ContentValues();
        ct.put("name", obj.getName());
        ct.put("idType", obj.getIdType());
        ct.put("mode", obj.getMode());
        ct.put("note", obj.getNote());
        if (db.update("Room", ct, "idRoom=?", new String[]{String.valueOf(obj.getIdRoom())}) == -1) {
            return -1;
        }
        return 1;
    }

    //Delete
    public int delete(String id) {
        if (db.delete("Room", "idRoom=?", new String[]{id}) == -1) {
            return -1;
        }
        return 1;
    }

    public void deleteAll() {
        db.execSQL("DELETE FROM Room");
    }

    //Show all
    public List<Room> getAll() {
        String sql = "SELECT * FROM Room";
        return getData(sql);
    }

    //get Data
    public List<Room> getData(String sql, String... select) {
        List<Room> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, select);
        while (cursor.moveToNext()) {
            Room obj = new Room(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getInt(3),
                    cursor.getString(4)
            );
            list.add(obj);
        }
        return list;
    }

    public Room getId(String id) {
        String sql = "SELECT * FROM Room WHERE idRoom=?";
        List<Room> typeList = getData(sql, id);
        if (typeList.isEmpty()) {
            return null;
        }
        return typeList.get(0);
    }
    public List<Room> showRoom() {
        String sql = "SELECT * FROM Room WHERE idRoom IN(" +
                "SELECT idRoom FROM Register)";
        List<Room> typeList = getData(sql);
        if (typeList.isEmpty()) {
            return null;
        }
        return typeList;
    }
}
