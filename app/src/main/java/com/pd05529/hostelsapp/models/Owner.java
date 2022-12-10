package com.pd05529.hostelsapp.models;

import java.io.Serializable;
import java.util.Date;

public class Owner implements Serializable {
    private int idOwner;
    private String name;
    private String username;
    private String password;
    private String email;
    private int phone;
    private String address;
    private int gender;
    private long cmnd;
    private Date date;

    public Owner() {
    }

    public Owner(int idOwner,String name, String username, String password, int phone, String email, String address, int gender, long cmnd, Date date) {
        this.idOwner = idOwner;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.gender = gender;
        this.cmnd = cmnd;
        this.date = date;
    }
    public Owner(String name, String username, String password, String email, int phone, String address, int gender) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.gender = gender;
    }

    public Owner(String name, String username, String password, String email, int phone, String address, int gender, long cmnd, Date date) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.gender = gender;
        this.cmnd = cmnd;
        this.date = date;
    }

    public long getCmnd() {
        return cmnd;
    }

    public Date getDate() {
        return date;
    }

    public String getEmail() {
        return email;
    }

    public void setIdOwner(int idOwner) {
        this.idOwner = idOwner;
    }

    public int getIdOwner() {
        return idOwner;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public int getGender() {
        return gender;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setCmnd(long cmnd) {
        this.cmnd = cmnd;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
