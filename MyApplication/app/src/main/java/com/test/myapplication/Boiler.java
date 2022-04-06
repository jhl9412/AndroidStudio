package com.test.myapplication;

public class Boiler {

    private int id;
    private int temp;
    private String status;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getTemp() {
        return temp;
    }
    public void setTemp(int temp) {
        this.temp = temp;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Boiler) {
            Boiler boiler = (Boiler)obj;
            if(this.status == boiler.status) return true;
            else return false;
        }
        return false;
    }
}
