package com.example.familytreerecipe;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class AddConnections extends AppCompatActivity {

    UserDBHelper udb;
    public int requestid;
    public String rname;
    ArrayList userslist;
    ArrayAdapter useradapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_connections);

        udb = new UserDBHelper(this);

        ListView Users = (ListView) findViewById(R.id.AllUsersView);
        userslist = udb.GetNonFriends(MainActivity.Userid);
        useradapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        Users.setAdapter(useradapter);
        useradapter.clear();
        useradapter.addAll(userslist);

        Users.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                rname = (String) useradapter.getItem(position);
                requestid = udb.GetUserId(rname);
                SendRequest();
            }
        });
    }
    public void SendRequest(){
        //udb.NewRequest(MainActivity.Userid, requestid)
        Intent intent = new Intent(this, SendRequest.class);

        intent.putExtra("FriendUN", rname);
        intent.putExtra( "FriendID", requestid);

        //intent.putExtras(data);
        startActivity(intent);
    }

}

