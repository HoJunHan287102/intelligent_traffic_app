package com.example.loginscreen;

public class HelperClass {

    String carPlate, carType, carColour, iD, type, summonDetail, summonRate, paymentDateline, date, time, location, status;

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }
    public String getCarPlate() {
        return carPlate;
    }
    public void setCarType(String carType) {
        this.carType = carType;
    }
    public String getCarType() {
        return carType;
    }
    public void setCarColour(String carColour) {
        this.carColour = carColour;
    }
    public String getCarColour() {
        return carColour;
    }

    public String getID() {
        return iD;
    }

    public void setID(String iD) {
        this.iD = iD;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

//    public String getSummonID() {
//        return summonID;
//    }
//
//    public void setSummonID(String summonID) {
//        this.summonID = summonID;
//    }

    public String getSummonDetail() {
        return summonDetail;
    }

    public void setSummonDetail(String summonDetail) {
        this.summonDetail = summonDetail;
    }
    public String getSummonRate() {
        return summonRate;
    }

    public void setSummonRate(String summonRate) {
        this.summonRate = summonRate;
    }
    public String getPaymentDateline() {
        return paymentDateline;
    }

    public void setPaymentDateline(String paymentDateline) {
        this.paymentDateline = paymentDateline;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public HelperClass(String carPlate, String carType, String carColour, String iD, String type,
                       String summonDetail, String summonRate, String paymentDateline, String date,
                       String time, String location, String status) {
        this.carPlate = carPlate;
        this.carType = carType;
        this.carColour = carColour;
        this.iD = iD;
        this.type = type;
//        this.summonID = summonID;
        this.summonDetail = summonDetail;
        this.summonRate = summonRate;
        this.paymentDateline = paymentDateline;
        this.date = date;
        this.time = time;
        this.location = location;
        this.status = status;
    }

    public HelperClass() {
    }
}

