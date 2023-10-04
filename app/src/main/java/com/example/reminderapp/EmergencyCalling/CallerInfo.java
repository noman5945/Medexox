package com.example.reminderapp.EmergencyCalling;

public class CallerInfo {
    //this class keep the caller data in firebase
    public String name1,num,id,email;

    public CallerInfo()
    {

    }

    public CallerInfo(String name1,String num,String id,String email)
    {
        this.name1=name1;
        this.num=num;
        this.id=id;
        this.email=email;
    }
}
