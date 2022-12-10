package com.pd05529.hostelsapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.pd05529.hostelsapp.database.DbHelper;
import com.pd05529.hostelsapp.models.Bill;
import com.pd05529.hostelsapp.support.Converter;

import java.util.ArrayList;
import java.util.List;

public class BillDAO {
    private SQLiteDatabase db;

    public BillDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        this.db = dbHelper.getWritableDatabase();
    }

    //Insert
    public long insert(Bill obj) {
        ContentValues ct = new ContentValues();
        ct.put("idRoom", obj.getIdRoom());
        ct.put("amount", obj.getAmount());
        ct.put("prePay", obj.getPrePay());
        ct.put("owe", obj.getOwe());
        ct.put("more", obj.getMore());
        ct.put("date", Converter.toTimestamp(obj.getDate()));
        ct.put("note", obj.getNote());
        ct.put("oldWater", obj.getOldWater());
        ct.put("newWater", obj.getNewWater());
        ct.put("oldElec", obj.getOldElec());
        ct.put("newElec", obj.getNewElec());
        if (db.insert("Bill", null, ct) == -1) {
            return -1;
        }
        return 1;
    }

    //Update
    public int update(Bill obj) {
        ContentValues ct = new ContentValues();
        ct.put("idRoom", obj.getIdRoom());
        ct.put("amount", obj.getAmount());
        ct.put("prePay", obj.getPrePay());
        ct.put("owe", obj.getOwe());
        ct.put("more", obj.getMore());
        ct.put("date", Converter.toTimestamp(obj.getDate()));
        ct.put("note", obj.getNote());
        ct.put("oldWater", obj.getOldWater());
        ct.put("newWater", obj.getNewWater());
        ct.put("oldElec", obj.getOldElec());
        ct.put("newElec", obj.getNewElec());
        if (db.update("Bill", ct, "idBill=?", new String[]{String.valueOf(obj.getIdBill())}) == -1) {
            return -1;
        }
        return 1;
    }

    //Delete
    public int delete(String id) {
        return db.delete("Bill", "idBill=?", new String[]{String.valueOf(id)});
    }

    public void deleteAll() {
        db.execSQL("DELETE FROM Bill");
    }

    //get All
    public List<Bill> getAll() {
        String sql = "SELECT * FROM Bill";
        return getData(sql);
    }

    //get data
    public List<Bill> getData(String sql, String... selection) {
        List<Bill> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selection);
        while (cursor.moveToNext()) {
            Bill obj = new Bill(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getLong(2),
                    cursor.getLong(3),
                    cursor.getLong(4),
                    cursor.getLong(5),
                    Converter.toDate(cursor.getString(6)),
                    cursor.getString(7),
                    cursor.getInt(8),
                    cursor.getInt(9),
                    cursor.getInt(10),
                    cursor.getInt(11));
            list.add(obj);
        }
        return list;
    }

    public List<Bill> showBillRoom(String id) {
        List<Bill> listBill = getData("SELECT * FROM Bill WHERE idRoom=?", id);
        if (listBill.isEmpty()) {
            return null;
        }
        return listBill;
    }
}
