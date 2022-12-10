package com.pd05529.hostelsapp.models;

import android.text.Html;

import java.io.Serializable;

public class RoomType implements Serializable {
    private int idType;
    private String name;
    private long price;
    private int squareArea;

    private int maxMember;

    public RoomType() {
    }

    public RoomType(int idType, String name, long price, int squareArea, int maxMember) {
        this.idType = idType;
        this.name = name;
        this.price = price;
        this.squareArea = squareArea;

        this.maxMember = maxMember;
    }

    public RoomType(String name, long price, int squareArea, int maxMember) {
        this.name = name;
        this.price = price;
        this.squareArea = squareArea;

        this.maxMember = maxMember;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getSquareArea() {
        return squareArea;
    }

    public void setSquareArea(int squareArea) {
        this.squareArea = squareArea;
    }

    public int getMaxMember() {
        return maxMember;
    }

    public void setMaxMember(int maxMember) {
        this.maxMember = maxMember;
    }

    @Override
    public String toString() {
        return idType + "-" + name + "-" + Html.fromHtml(squareArea + " m<sup>2</sup>") + "-Tối đa: " + maxMember;
    }
}
