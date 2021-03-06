package com.vladlanda.firebasetut;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MessagesActivity extends AppCompatActivity {


    DatabaseReference dbRootRef= FirebaseDatabase.getInstance().getReference();
    DatabaseReference dbCondRef  =  dbRootRef.child("team_messages");

    TextView textView;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.viewMsg);
        editText = (EditText)findViewById(R.id.editToSend);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Database value listener
        dbCondRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
//                Toast.makeText(getApplicationContext(),"Hi",Toast.LENGTH_SHORT).show();
                textView.setText(text);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    //Send value to database
    public void sendClicked(View v){
        dbCondRef.setValue(editText.getText().toString());
    }
}
