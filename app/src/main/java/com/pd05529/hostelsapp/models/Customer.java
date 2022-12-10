package com.pd05529.hostelsapp.models;

import java.io.Serializable;
import java.util.Date;

public class Customer implements Serializable {
    private String idCus;
    private String name;
    private int gender;
    private String email;
    private long phone;
    private long cmnd;
    private String address;
    private Date birthDay;

    public Customer() {
    }

    public Customer(String idCus, String name, int gender, String email, long phone, long cmnd, String address, Date birthDay) {
        this.idCus = idCus;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.cmnd = cmnd;
        this.address = address;
        this.birthDay = birthDay;
    }

    public String getIdCus() {
        return idCus;
    }

    public void setIdCus(String idCus) {
        this.idCus = idCus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public long getCmnd() {
        return cmnd;
    }

    public void setCmnd(long cmnd) {
        this.cmnd = cmnd;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    @Override
    public String toString() {
        return idCus + " - " + name + " - " + phone;
    }
}
