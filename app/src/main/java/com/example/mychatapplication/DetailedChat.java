package com.example.mychatapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;


public class DetailedChat extends AppCompatActivity {

    String useridbor;
    FirebaseDatabase firedb;
    DatabaseReference dbref,dbrefref,dbref1;
    TextView oppositeuser;
    ListView listView;
    Message msgobj;
    EditText sendmsg;
    static int idvalue=100;

    List<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    FirebaseUser fuser;
    String curruid;

    public DatabaseReference mdatabase;

    String fromusername,tousername;

    FloatingActionButton fab1;

    Date currentTime;
    int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_chat);

        Intent intent=getIntent();
        useridbor= intent.getStringExtra("user_id");
        sendmsg=(EditText) findViewById(R.id.idsendmsg);

        fuser= FirebaseAuth.getInstance().getCurrentUser();
        curruid=fuser.getUid();

        currentTime = Calendar.getInstance().getTime();

        firedb = FirebaseDatabase.getInstance();
        dbref = firedb.getReference("messages");
        dbrefref=firedb.getReference("users");
        dbref1=firedb.getReference("messages");

        listView = (ListView) findViewById(R.id.idlistviewdetail);
        oppositeuser=(TextView) findViewById(R.id.idoppuser);

        fab1=(FloatingActionButton) findViewById(R.id.idfabsend);

        mdatabase= FirebaseDatabase.getInstance().getReference();

        dbrefref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User userobj = ds.getValue(User.class);
                    if(userobj.userid.equals(useridbor)){
                        tousername=userobj.name;
                    }
                    else if(userobj.userid.equals(curruid)){
                        fromusername=userobj.name;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        oppositeuser.setText(tousername);

        dbref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    count++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    msgobj = ds.getValue(Message.class);
                    if((msgobj.from.equals(curruid)&&msgobj.to.equals(useridbor)) ){
                        arrayList.add(fromusername+" : "+msgobj.content+"  : "+msgobj.dateval);
                    }
                    if((msgobj.from.equals(useridbor)&& msgobj.to.equals(curruid))){
                        arrayList.add(tousername+" : "+msgobj.content+"  : "+msgobj.dateval);
                    }
                }
                arrayAdapter = new ArrayAdapter<String>(DetailedChat.this, android.R.layout.simple_list_item_1, arrayList);
                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writenewmsg(curruid,useridbor, sendmsg.getText().toString());

            }
        });
    }



    public void writenewmsg(String from,String to,String content){
        Date date = new Date();
        String stringDate = DateFormat.getDateTimeInstance().format(date);
        Message msg=new Message(from,to, content, stringDate);

        mdatabase.child("messages").child(Integer.toString(count+1)).setValue(msg);

    }
}
