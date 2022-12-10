package com.pd05529.hostelsapp.DAO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.pd05529.hostelsapp.database.DbHelper;
import com.pd05529.hostelsapp.models.Bill;
import com.pd05529.hostelsapp.models.InCome;
import com.pd05529.hostelsapp.models.Room;

import java.util.ArrayList;
import java.util.List;

public class InComeDAO {
    private SQLiteDatabase db;
    private Context context;
    public InComeDAO(Context context){
        this.context = context;
        DbHelper dbHelper = new DbHelper(context);
        this.db = dbHelper.getWritableDatabase();
    }
    @SuppressLint("Range")
    public long getTotal(){
        String sql = "Select sum(amount)as tong FROM Bill";
        List<Integer> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,null);
        while (cursor.moveToNext()){
            try {
                list.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("tong"))));
            }catch (NumberFormatException e){
                    return 0;
            }
        }
        return list.get(0);
    }

    @SuppressLint("Range")
    public long getPay(){
        String sql = "Select sum(prePay) as tra FROM Bill";
        List<Integer> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,null);
        while (cursor.moveToNext()){
            try {
                list.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("tra"))));
            }catch (NumberFormatException e){
                return 0;
            }
        }
        return list.get(0);
    }

    @SuppressLint("Range")
    public long getOwe(){
        String sql = "Select sum(owe) as soNo FROM Bill";
        List<Integer> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,null);
        while (cursor.moveToNext()){
            try {
                list.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("soNo"))));
            }catch (NumberFormatException e){
                return 0;
            }
        }
        return list.get(0);
    }
    @SuppressLint("Range")
    public List<InCome> getInCome(){
        String sql = "Select idRoom,sum(amount)as tong,sum(prePay) as tra,sum(owe) as soNo,count(idRoom) as soluong FROM Bill " +
                "GROUP BY idRoom ";
        List<InCome> list = new ArrayList<>();
        RoomDAO roomDAO = new RoomDAO(context);
        Cursor cursor = db.rawQuery(sql,null);
        while (cursor.moveToNext()){
            InCome inCome = new InCome();
            Room bill =roomDAO.getId(cursor.getString(cursor.getColumnIndex("idRoom")));
            inCome.setName(bill.getName());
            inCome.setTotal(Integer.parseInt(cursor.getString(cursor.getColumnIndex("tong"))));
            inCome.setOwes(Integer.parseInt(cursor.getString(cursor.getColumnIndex("soNo"))));
            inCome.setPay(Integer.parseInt(cursor.getString(cursor.getColumnIndex("tra"))));
            inCome.setCount(Integer.parseInt(cursor.getString(cursor.getColumnIndex("soluong"))));
            list.add(inCome);
        }
        return list;
    }
    @SuppressLint("Range")
    public List<InCome> getInComeRoom(String date){
        String sql = "Select idRoom,sum(amount)as tong,sum(prePay) as tra,sum(owe) as soNo,count(idRoom) as soluong FROM Bill " +
                "where strftime('%m-%Y')=?" +
                "GROUP bY idRoom";
        List<InCome> list = new ArrayList<>();
        RoomDAO roomDAO = new RoomDAO(context);
        Cursor cursor = db.rawQuery(sql,new String[]{date});
        while (cursor.moveToNext()){
            InCome inCome = new InCome();
            Room bill =roomDAO.getId(cursor.getString(cursor.getColumnIndex("idRoom")));
            inCome.setName(bill.getName());
            inCome.setTotal(Integer.parseInt(cursor.getString(cursor.getColumnIndex("tong"))));
            inCome.setOwes(Integer.parseInt(cursor.getString(cursor.getColumnIndex("soNo"))));
            inCome.setPay(Integer.parseInt(cursor.getString(cursor.getColumnIndex("tra"))));
            inCome.setCount(Integer.parseInt(cursor.getString(cursor.getColumnIndex("soluong"))));
            list.add(inCome);
        }
        return list;
    }
}
