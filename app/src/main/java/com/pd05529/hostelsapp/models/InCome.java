package com.pd05529.hostelsapp.models;

public class InCome {
    private String name;
    private long total;
    private long pay;
    private long owes;
    private int count;

    public InCome(String name, long total, long pay, long owes, int count) {
        this.name = name;
        this.total = total;
        this.pay = pay;
        this.owes = owes;
        this.count = count;
    }

    public InCome(long total, long pay, long owes) {
        this.total = total;
        this.pay = pay;
        this.owes = owes;
    }

    public InCome() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getPay() {
        return pay;
    }

    public void setPay(long pay) {
        this.pay = pay;
    }

    public long getOwes() {
        return owes;
    }

    public void setOwes(long owes) {
        this.owes = owes;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
