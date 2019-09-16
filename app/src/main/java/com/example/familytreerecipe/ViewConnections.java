package com.example.familytreerecipe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ViewConnections extends AppCompatActivity {
    UserDBHelper udb;

    ArrayList<String> ConnectionList;
    ArrayAdapter ConnectionAdapter;
    //Button pgbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_connections);

        udb = new UserDBHelper(this);

        ListView Friends = (ListView) findViewById(R.id.FriendListView);
        ConnectionList = udb.GetConnections(MainActivity.Userid);
        ConnectionAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        Friends.setAdapter(ConnectionAdapter);
        ConnectionAdapter.clear();
        ConnectionAdapter.addAll(ConnectionList);

        //pgbtn = (Button) findViewById(R.id.AddCBtn);
    }

    public void AddConnection(View view){
        Intent intent = new Intent(this, AddConnections.class);
        startActivity(intent);
    }

    public void AcceptRequests(View view){
        Intent intent = new Intent(this, Request_Connections.class);
        startActivity(intent);
    }
}
