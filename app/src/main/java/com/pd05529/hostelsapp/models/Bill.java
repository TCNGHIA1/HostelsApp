package com.pd05529.hostelsapp.models;

import java.io.Serializable;
import java.util.Date;

public class Bill implements Serializable {
    private int idBill;
    private String idRoom;
    private long amount, prePay, owe, more;
    private Date date;
    private String note;
    private int oldWater, newWater, oldElec, newElec;

    public Bill() {
    }

    public Bill(int idBill, String idRoom, long amount, long prePay, long owe, long more, Date date, String note, int oldWater, int newWater, int oldElec, int newElec) {
        this.idBill = idBill;
        this.idRoom = idRoom;
        this.amount = amount;
        this.prePay = prePay;
        this.owe = owe;
        this.more = more;
        this.date = date;
        this.note = note;
        this.oldWater = oldWater;
        this.newWater = newWater;
        this.oldElec = oldElec;
        this.newElec = newElec;
    }

    public Bill(String idRoom, long amount, long prePay, long owe, long more, Date date, String note, int oldWater, int newWater, int oldElec, int newElec) {
        this.idRoom = idRoom;
        this.amount = amount;
        this.prePay = prePay;
        this.owe = owe;
        this.more = more;
        this.date = date;
        this.note = note;
        this.oldWater = oldWater;
        this.newWater = newWater;
        this.oldElec = oldElec;
        this.newElec = newElec;
    }

    public int getIdBill() {
        return idBill;
    }

    public void setIdBill(int idBill) {
        this.idBill = idBill;
    }

    public String getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(String idRoom) {
        this.idRoom = idRoom;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getPrePay() {
        return prePay;
    }

    public void setPrePay(long prePay) {
        this.prePay = prePay;
    }

    public long getOwe() {
        return owe;
    }

    public void setOwe(long owe) {
        this.owe = owe;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getOldWater() {
        return oldWater;
    }

    public void setOldWater(int oldWater) {
        this.oldWater = oldWater;
    }

    public int getNewWater() {
        return newWater;
    }

    public void setNewWater(int newWater) {
        this.newWater = newWater;
    }

    public int getOldElec() {
        return oldElec;
    }

    public void setOldElec(int oldElec) {
        this.oldElec = oldElec;
    }

    public int getNewElec() {
        return newElec;
    }

    public void setNewElec(int newElec) {
        this.newElec = newElec;
    }

    public long getMore() {
        return more;
    }

    public void setMore(long more) {
        this.more = more;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "idRoom='" + idRoom + '\'' +
                ", amount=" + amount +
                ", prePay=" + prePay +
                ", owe=" + owe +
                '}';
    }
}
