package com.example.familytreerecipe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Request_Connections extends AppCompatActivity {

    UserDBHelper udb;
    ArrayList<String> relist;
    ArrayAdapter reAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request__connections);

        udb = new UserDBHelper(this);

        ListView Rlist = (ListView) findViewById(R.id.UserRequestListView);
        relist = udb.GetAllRequests(MainActivity.Userid);
        reAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        Rlist.setAdapter(reAdapter);
        reAdapter.clear();
        reAdapter.addAll(relist);
        Rlist.setOnItemClickListener(new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String rname = (String) reAdapter.getItem(position);
            Intent intent = new Intent(getApplicationContext(), Accept_Connection.class);
            intent.putExtra("ReName", rname);
            startActivity(intent);
        }});
    }

    public void DeleteAllRequests(View view){
        udb.DeleteAllRequests(MainActivity.Userid);
    }

}
