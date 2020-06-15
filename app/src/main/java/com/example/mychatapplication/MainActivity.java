package com.example.mychatapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    FirebaseUser fuser;
    ListView listView;
    FirebaseDatabase firedb;
    DatabaseReference dbref,dbrefref;
    List<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    FloatingActionButton fabutton;
    Set<String> hash_Set = new HashSet<String>();

    String curruid;
    Message msgobj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firedb = FirebaseDatabase.getInstance();
        dbref = firedb.getReference("messages");
        dbrefref =firedb.getReference("users");

        fuser= FirebaseAuth.getInstance().getCurrentUser();
        curruid=fuser.getUid();
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.idlistviewmain);

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    msgobj = ds.getValue(Message.class);
                    if(msgobj.from.equals(curruid)){
                        hash_Set.add(msgobj.to);
                    }
                }
                for (String i : hash_Set) {
                    arrayList.add(i);
                }
                arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, arrayList);
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


                                Intent intent=new Intent(getApplicationContext(), DetailedChat.class);
                                intent.putExtra("user_id", name11);
                                startActivity(intent);

            }
        });
       /* dbrefref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dst : dataSnapshot.getChildren()) {
                    User userobj = dst.child("users").getValue(User.class);
                    for (String i : hash_Set){
                        if(userobj.userid.equals(i)){
                            arrayList.add(userobj.name);
                        }
                    }
                }
                arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, arrayList);
                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        fabutton=(FloatingActionButton) findViewById(R.id.idfab);

        fabutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NewChat.class));
            }
        });
    }
}
