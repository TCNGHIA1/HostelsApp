package com.pd05529.hostelsapp.models;

import java.io.Serializable;
import java.util.Date;

public class Ser implements Serializable {
    private int idSer;
    private int price;
    private Date date;

    public Ser() {
    }

    public Ser(int idSer, int price, Date date) {
        this.idSer = idSer;
        this.price = price;
        this.date = date;
    }

    public int getIdSer() {
        return idSer;
    }

    public void setIdSer(int idSer) {
        this.idSer = idSer;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Ser{" +
                "idSer=" + idSer +
                ", price=" + price +
                ", date=" + date +
                '}';
    }
}
