package com.pd05529.hostelsapp.models;

import java.io.Serializable;

public class Reg_info implements Serializable {
    private int id,idReg;
    private String idCus;

    public Reg_info() {
    }

    public Reg_info(int id, int idReg, String idCus) {
        this.id = id;
        this.idReg = idReg;
        this.idCus = idCus;
    }

    public Reg_info(int idReg, String idCus) {
        this.idReg = idReg;
        this.idCus = idCus;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getIdReg() {
        return idReg;
    }

    public String getIdCus() {
        return idCus;
    }
}
