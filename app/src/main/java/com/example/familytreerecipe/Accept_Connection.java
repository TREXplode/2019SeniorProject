package com.example.familytreerecipe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Accept_Connection extends AppCompatActivity {

    UserDBHelper udb;
    String rname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept__connection);
        udb = new UserDBHelper(this);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            rname = extras.getString("ReName");
        }

        TextView Rview = findViewById(R.id.RequestView);
        Rview.setText("Accept request from " + rname + "?");
    }

    public void Accept(View view){
        int rid = udb.GetUserId(rname);
        udb.AddConnection(MainActivity.Userid, rid);
        udb.AddConnection(rid, MainActivity.Userid);
        udb.DeleteRequest(rid, MainActivity.Userid);

        Intent intent = new Intent(this, MainActivityLogin.class);
        startActivity(intent);
    }
    public void Delete(View view){
        int rid = udb.GetUserId(rname);
        udb.DeleteRequest(rid, MainActivity.Userid);
        Intent intent = new Intent(this, MainActivityLogin.class);
        startActivity(intent);
    }

}
