package com.example.mychatapplication;

public class User {
    public String name;
    public String userid;
    public String emailid;

    public User(){

    }
    public User(String userid,String name,String emailid){
        this.userid=userid;
        this.name=name;
        this.emailid=emailid;
    }
    public String getUserid(){
        return userid;
    }

    public String getName(){
        return name;

    }

    public String getEmailid(){
        return emailid;
    }
}
