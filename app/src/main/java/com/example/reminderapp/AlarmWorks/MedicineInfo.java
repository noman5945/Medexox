package com.example.reminderapp.AlarmWorks;

public class MedicineInfo {
    public String medicineName, time, date;
    public int ID, image;

    public MedicineInfo() {

    }

    public MedicineInfo(String medicineName, String time, String date, int ID, int image) {
        this.medicineName = medicineName;
        this.time = time;
        this.date = date;
        this.ID = ID;
        this.image = image;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
