package com.pd05529.hostelsapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.pd05529.hostelsapp.database.DbHelper;
import com.pd05529.hostelsapp.models.Ser;
import com.pd05529.hostelsapp.support.Converter;

import java.util.ArrayList;
import java.util.List;

public class SerDAO {
    private SQLiteDatabase db;

    public SerDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        this.db = dbHelper.getWritableDatabase();
    }

    //Insert
    public long insert(Ser obj) {
        ContentValues ct = new ContentValues();
        ct.put("price", obj.getPrice());
        ct.put("date", Converter.toTimestamp(obj.getDate()));
        return db.insert("Ser", null, ct);
    }

    //Update
    public long update(Ser obj) {
        ContentValues ct = new ContentValues();
        ct.put("price", obj.getPrice());
        ct.put("date", Converter.toTimestamp(obj.getDate()));
        if (db.update("Ser", ct, "idSer=?", new String[]{String.valueOf(obj.getIdSer())}) == -1) {
            return -1;
        }
        return 1;
    }

    //Delete
    public int delete(String id) {
        return db.delete("Ser", "idSer=?", new String[]{id});
    }

    //Show all
    public List<Ser> getAll() {
        String sql = "SELECT * FROM Ser";
        return getData(sql);
    }

    //get Data
    public List<Ser> getData(String sql, String... select) {
        List<Ser> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, select);
        while (cursor.moveToNext()) {
            Ser obj = new Ser(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    Converter.toDate(cursor.getString(2))
            );
            list.add(obj);
        }
        return list;
    }

    public Ser getId(String id) {
        List<Ser> sers = getData("SELECT * FROM Ser where idSer=?", id);
        if (sers != null) {
            return sers.get(0);
        }
        return null;
    }
}
