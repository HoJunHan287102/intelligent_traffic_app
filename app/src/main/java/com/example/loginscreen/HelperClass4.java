package com.example.loginscreen;

public class HelperClass4 {

    String email, password, officerID, officerName, officerPosition, uid;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getOfficerID() {
        return officerID;
    }

    public void setOfficerID(String officerID) {
        this.officerID = officerID;
    }
    public String getOfficerName() {
        return officerName;
    }

    public void setOfficerName(String officerName) {
        this.officerName = officerName;
    }
    public String getOfficerPosition() {
        return officerPosition;
    }
    public void setOfficerPosition(String officerPosition) {
        this.officerPosition = officerPosition;
    }
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }

    public HelperClass4(String email, String password, String officerID, String officerName, String officerPosition, String uid) {
        this.email = email;
        this.password = password;
        this.officerID = officerID;
        this.officerName = officerName;
        this.officerPosition = officerPosition;
        this.uid = uid;
    }

    public HelperClass4() {
    }
}
