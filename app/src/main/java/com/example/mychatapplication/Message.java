package com.example.mychatapplication;

import java.util.Date;

public class Message {
    public String from;
    public String to;
    public String content;
    public String dateval;
    public int currmax=10000;
    //public String msgid;

    public Message(){

    }
    public Message(String from,String to,String content, String dateval){
        this.from=from;
        this.to=to;
        this.content=content;
        this.dateval=dateval;
    }
    public int getCurrmax(){
        return  currmax;
    }
    public void setCurrmax(){
        currmax++;
    }
    public String getFrom(){
        return from;
    }
}
