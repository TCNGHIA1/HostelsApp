package com.pd05529.hostelsapp.models;

import java.io.Serializable;

public class Room implements Serializable {
    private String idRoom;
    private String name;
    private int idType;
    private int mode;
    private String note;

    public Room() {
    }

    public Room(String idRoom, String name, int idType, int mode, String note) {
        this.idRoom = idRoom;
        this.name = name;
        this.idType = idType;
        this.mode = mode;
        this.note = note;
    }


    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public String getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(String idRoom) {
        this.idRoom = idRoom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return idRoom + " - " + name;
    }
}
