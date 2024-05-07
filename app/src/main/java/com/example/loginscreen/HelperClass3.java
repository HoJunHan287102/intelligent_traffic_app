package com.example.loginscreen;

public class HelperClass3 {
    String officerID, officerName, officerPosition, uid;
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
    public HelperClass3(String officerID, String officerName, String officerPosition, String uid) {
        this.officerID = officerID;
        this.officerName = officerName;
        this.officerPosition = officerPosition;
        this.uid = uid;
    }

    public HelperClass3() {
    }
}
