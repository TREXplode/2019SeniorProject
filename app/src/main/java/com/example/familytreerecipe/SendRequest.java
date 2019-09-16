package com.example.familytreerecipe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SendRequest extends AppCompatActivity {

    String fname;
    int fid;
    UserDBHelper udb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_request);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            fname = extras.getString("FriendUN");
            fid = extras.getInt("FriendID");
        }
        udb = new UserDBHelper(this);

        TextView Sendv = findViewById(R.id.SendText);
        Sendv.setText("Are you sure you want to send a friend\nrequest to "
                    + fname + "?");
    }
    public void SendRequest(View view){
        udb.NewRequest(MainActivity.Userid, fid);
        Intent intent = new Intent(this, MainActivityLogin.class);
        startActivity(intent);
    }
}
