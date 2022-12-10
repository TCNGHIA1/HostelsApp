package com.pd05529.hostelsapp.models;

import java.io.Serializable;
import java.util.Date;

public class Register implements Serializable {
    private int id;
    private String idRoom;
    private Date dateReg, dateOut;

    public Register() {
    }

    public Register(int id, String idRoom, Date dateReg, Date dateOut) {
        this.id = id;
        this.idRoom = idRoom;
        this.dateReg = dateReg;
        this.dateOut = dateOut;
    }

    public Register( String idRoom, Date dateReg, Date dateOut) {
        this.idRoom = idRoom;
        this.dateReg = dateReg;
        this.dateOut = dateOut;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(String idRoom) {
        this.idRoom = idRoom;
    }

    public Date getDateReg() {
        return dateReg;
    }

    public void setDateReg(Date dateReg) {
        this.dateReg = dateReg;
    }

    public Date getDateOut() {
        return dateOut;
    }

    public void setDateOut(Date dateOut) {
        this.dateOut = dateOut;
    }

    @Override
    public String toString() {
        return "Register{" +
                "id=" + id +
                ", idRoom='" + idRoom + '\'' +
                ", dateReg=" + dateReg +
                ", dateOut=" + dateOut +
                '}';
    }
}
