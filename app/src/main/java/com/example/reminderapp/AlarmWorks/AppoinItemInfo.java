package com.example.reminderapp.AlarmWorks;

public class AppoinItemInfo {
    public String DocName,descr,time,date,status;
    public int imageRec,ID;

    public AppoinItemInfo()
    {

    }

    public AppoinItemInfo(String docName, String descr, String time, String date, String status, int ID,int imageRec) {
        DocName = docName;
        this.descr = descr;
        this.time = time;
        this.date = date;
        this.status = status;
        this.imageRec = imageRec;
        this.ID=ID;
    }

    public void setDocName(String docName) {
        DocName = docName;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setImageRec(int imageRec) {
        this.imageRec = imageRec;
    }

    public String getDocName() {
        return DocName;
    }

    public String getDescr() {
        return descr;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public int getImageRec() {
        return imageRec;
    }

    public int getID()
    {
        return ID;
    }
}
