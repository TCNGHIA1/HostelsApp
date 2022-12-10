package com.pd05529.hostelsapp.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.pd05529.hostelsapp.database.DbHelper;
import com.pd05529.hostelsapp.models.Customer;
import com.pd05529.hostelsapp.support.Converter;

import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    private SQLiteDatabase db;
    private Context context;

    public CustomerDAO(Context context) {
        this.context = context;
        DbHelper dbHelper = new DbHelper(context);
        this.db = dbHelper.getWritableDatabase();
    }

    //Insert
    public long insert(Customer obj) {
        ContentValues ct = new ContentValues();
        ct.put("idCus", obj.getIdCus().toUpperCase());
        ct.put("name", obj.getName());
        ct.put("gender", obj.getGender());
        ct.put("email", obj.getEmail());
        ct.put("phone", obj.getPhone());
        ct.put("cmnd", obj.getCmnd());
        ct.put("address", obj.getAddress());
        ct.put("birthDay", Converter.toTimestamp(obj.getBirthDay()));
        if (db.insert("Customer", null, ct) == -1) {
            return -1;
        }
        return 1;
    }

    //Update
    public long update(Customer obj) {
        ContentValues ct = new ContentValues();
        ct.put("name", obj.getName());
        ct.put("gender", obj.getGender());
        ct.put("email", obj.getEmail());
        ct.put("phone", obj.getPhone());
        ct.put("cmnd", obj.getCmnd());
        ct.put("address", obj.getAddress());
        ct.put("birthDay", Converter.toTimestamp(obj.getBirthDay()));
        if (db.update("Customer", ct, "idCus=?", new String[]{obj.getIdCus()}) == -1) {
            return -1;
        }
        return 1;
    }

    //Delete
    public int delete(String id) {
        return db.delete("Customer", "idCus=?", new String[]{id});
    }

    public void deleteAll() {
        String sql = "DELETE FROM Customer";
        db.execSQL(sql);
    }

    //Show all
    public List<Customer> getAll() {
        String sql = "SELECT * FROM Customer";
        return getData(sql);
    }

    //get Data
    @SuppressLint("Range")
    public List<Customer> getData(String sql, String... select) {
        List<Customer> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, select);
        while (cursor.moveToNext()) {
            Customer obj = new Customer(
                    cursor.getString(cursor.getColumnIndex("idCus")),
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getInt(cursor.getColumnIndex("gender")),
                    cursor.getString(cursor.getColumnIndex("email")),
                    cursor.getLong(cursor.getColumnIndex("phone")),
                    cursor.getLong(cursor.getColumnIndex("cmnd")),
                    cursor.getString(cursor.getColumnIndex("address")),
                    Converter.toDate(cursor.getString(cursor.getColumnIndex("birthDay")))
            );
            list.add(obj);
        }
        return list;
    }

    public Customer getId(String id) {
        List<Customer> list = getData("SELECT * FROM Customer WHERE idCus=?", id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    //Show all customer not register room
    public List<Customer> showCustomer() {
        List<Customer> list = getData("SELECT * FROM Customer " +
                "WHERE idCus NOT IN " +
                "(SELECT idCus FROM Reg_info)");
        if (list.isEmpty()) {
            return null;
        }
        return list;
    }

    //Show all customer in room
    public List<Customer> showCustomerIn(String id) {
        List<Customer> list = getData("SELECT * FROM Customer " +
                "WHERE idCus IN " +
                "(SELECT idCus FROM Reg_info WHERE idReg IN (SELECT id FROM Register WHERE  idRoom =?))", id);
        if (list.isEmpty()) {
            return null;
        }
        return list;
    }
}
