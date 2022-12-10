package com.pd05529.hostelsapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.pd05529.hostelsapp.database.DbHelper;
import com.pd05529.hostelsapp.models.Customer;
import com.pd05529.hostelsapp.models.Register;
import com.pd05529.hostelsapp.support.Converter;

import java.util.ArrayList;
import java.util.List;

public class RegisterDAO {
    private SQLiteDatabase db;
    private Context context;

    public RegisterDAO(Context context) {
        this.context = context;
        DbHelper dbHelper = new DbHelper(context);
        this.db = dbHelper.getWritableDatabase();
    }

    //Insert
    public long insert(Register obj) {
        ContentValues ct = new ContentValues();
        ct.put("idRoom", obj.getIdRoom());
        ct.put("dateReg", Converter.toTimestamp(obj.getDateReg()));
        ct.put("dateOut", Converter.toTimestamp(obj.getDateOut()));
        if (db.insert("Register", null, ct) == -1) {
            return -1;
        }
        return 1;
    }

    //Update
    public int update(Register obj) {
        ContentValues ct = new ContentValues();
        ct.put("idRoom", obj.getIdRoom());
        ct.put("dateReg", Converter.toTimestamp(obj.getDateReg()));
        ct.put("dateOut", Converter.toTimestamp(obj.getDateOut()));
        if (db.update("Register", ct, "id=?", new String[]{String.valueOf(obj.getId())}) == -1) {
            return -1;
        }
        return 1;
    }

    //Delete
    public int delete(String id) {
        return db.delete("Register", "id=?", new String[]{String.valueOf(id)});
    }

    public void deleteAll() {
        db.execSQL("DELETE FROM Register");
    }

    public int deleteRoom(String id) {
        return db.delete("Register", "idRoom=?", new String[]{String.valueOf(id)});
    }

    //get All
    public List<Register> getAll() {
        String sql = "SELECT * FROM Register";
        return getData(sql);
    }

    //get data
    public List<Register> getData(String sql, String... selection) {
        List<Register> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selection);
        while (cursor.moveToNext()) {
            Register obj = new Register(cursor.getInt(0),
                    cursor.getString(1),
                    Converter.toDate(cursor.getString(2)),
                    Converter.toDate(cursor.getString(3)));
            list.add(obj);
        }
        return list;
    }


    public Register getId(String id) {
        List<Register> list = getData("SELECT * FROM Register WHERE idRoom=?", id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
}
