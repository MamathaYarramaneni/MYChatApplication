package com.example.mychatapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NewChat extends AppCompatActivity {

    FirebaseUser fuser;
    ListView listView;
    FirebaseDatabase firedb;
    DatabaseReference dbref;
    List<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    String curruid;
    User userobj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_chat);

        firedb = FirebaseDatabase.getInstance();
        dbref = firedb.getReference("users");

        fuser= FirebaseAuth.getInstance().getCurrentUser();
        curruid=fuser.getUid();

        listView = (ListView) findViewById(R.id.idlistviewnew);


        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    userobj = ds.getValue(User.class);
                        if(!userobj.userid.equals(curruid)) {
                            arrayList.add(userobj.name);
                        }
                }
                arrayAdapter = new ArrayAdapter<String>(NewChat.this, android.R.layout.simple_list_item_1, arrayList);
                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String name11=arrayList.get(position).toString();
                dbref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            userobj = ds.getValue(User.class);

                                if(userobj.name==name11){
                                    String userid=userobj.userid.toString();
                                    Intent intent=new Intent(getApplicationContext(), DetailedChat.class);
                                    intent.putExtra("user_id", userid);
                                    startActivity(intent);
                                }


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
