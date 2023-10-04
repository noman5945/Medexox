package com.example.reminderapp.UserAuth;

public class User {
    public String username, email, age, Usertype, password, gender;

    public User()
    {

    }
    public User(String username,String email,String age,String Usertype,String gender,String password)
    {
        this.username=username;
        this.email=email;
        this.age=age;
        this.Usertype=Usertype;
        this.gender=gender;
        this.password=password;
    }
}
