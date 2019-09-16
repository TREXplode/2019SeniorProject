package com.example.familytreerecipe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity {
    UserDBHelper userdb;

    EditText username;
    EditText password;
    TextView WrongPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userdb = new UserDBHelper(this);
        username = (EditText) findViewById(R.id.UsernameReg);
        password = (EditText) findViewById(R.id.Password);
        WrongPass = (TextView) findViewById(R.id.WrongPass);

        WrongPass.setVisibility(View.INVISIBLE);
    }

    public void CheckPassword(View view){
        boolean correct = userdb.CheckPassword(username.getText().toString(), password.getText().toString());
        int uid = userdb.GetUserId(username.getText().toString());

        if(correct){
            MainActivity.logged_in = true;
            MainActivity.Userid = uid;
            Intent intent = new Intent(this, MainActivityLogin.class);
            startActivity(intent);
        }
        else
        {
            WrongPass.setVisibility(View.VISIBLE);
        }
    }
    public void NewUser(View view){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
}
