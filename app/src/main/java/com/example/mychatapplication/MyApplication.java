package com.example.mychatapplication;

import android.app.Application;

public class MyApplication extends Application {
    private int someVariable=10000;

    public int getSomeVariable() {
        return someVariable;
    }

    public void setSomeVariable(int someVariable) {
        this.someVariable = someVariable;
    }
}
