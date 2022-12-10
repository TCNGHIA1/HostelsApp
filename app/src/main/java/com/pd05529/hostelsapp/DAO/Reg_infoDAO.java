package com.pd05529.hostelsapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.pd05529.hostelsapp.database.DbHelper;
import com.pd05529.hostelsapp.models.Reg_info;
import com.pd05529.hostelsapp.support.Converter;

import java.util.ArrayList;
import java.util.List;

public class Reg_infoDAO {
    private SQLiteDatabase db;
    private Context context;

    public Reg_infoDAO(Context context) {
        this.context = context;
        DbHelper dbHelper = new DbHelper(context);
        this.db = dbHelper.getWritableDatabase();
    }

    //Insert
    public long insert(Reg_info obj) {
        ContentValues ct = new ContentValues();
        ct.put("idReg", obj.getIdReg());
        ct.put("idCus", obj.getIdCus());
        if (db.insert("Reg_info", null, ct) == -1) {
            return -1;
        }
        return 1;
    }

    //Update
    public int update(Reg_info obj) {
        ContentValues ct = new ContentValues();
        ct.put("idReg", obj.getIdReg());
        ct.put("idCus", obj.getIdCus());
        if (db.update("Reg_info", ct, "id=?", new String[]{String.valueOf(obj.getId())}) == -1) {
            return -1;
        }
        return 1;
    }

    //Delete
    public int delete(String id) {
        return db.delete("Reg_info", "id=?", new String[]{String.valueOf(id)});
    }

    public void deleteAll() {
        db.execSQL("DELETE FROM Reg_info");
    }

    public int deleteReg(String id) {
        return db.delete("Reg_info", "idReg=?", new String[]{String.valueOf(id)});
    }

    public int deleteCustom(String id) {
        return db.delete("Reg_info", "idCus=?", new String[]{String.valueOf(id)});
    }

    //get All
    public List<Reg_info> getAll() {
        String sql = "SELECT * FROM Reg_info";
        return getData(sql);
    }

    //get data
    public List<Reg_info> getData(String sql, String... selection) {
        List<Reg_info> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selection);
        while (cursor.moveToNext()) {
            Reg_info obj = new Reg_info(cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2));
            list.add(obj);
        }
        return list;
    }
}
