package com.example.familytreerecipe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Register extends AppCompatActivity {
    UserDBHelper UseDB;
    EditText UserReg;
    EditText PassReg;
    EditText PassCon;
    TextView PassMatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        UseDB = new UserDBHelper(this);

        UserReg = (EditText) findViewById(R.id.UsernameReg);
        PassReg = (EditText) findViewById(R.id.PasswordReg);
        PassCon = (EditText) findViewById(R.id.PasswordConfirm);
        PassMatch = (TextView) findViewById(R.id.WrongPassTxt);
        PassMatch.setVisibility(View.INVISIBLE);
    }

    public void Registeruser(View view){
        if(UseDB.CheckUserName(UserReg.getText().toString())) {
            String Pass1 = PassCon.getText().toString();
            String Pass2 = PassReg.getText().toString();
            if (Pass1.contentEquals(Pass2)) {
                    UseDB.AddUser(UserReg.getText().toString(), PassReg.getText().toString());
                    MainActivity.logged_in = true;
                    MainActivity.Userid = UseDB.GetUserId(UserReg.getText().toString());
                    Intent intent = new Intent(this, MainActivityLogin.class);
                    startActivity(intent);
            } else {
                PassMatch.setVisibility(View.VISIBLE);
            }
        }
        else
        {
            PassMatch.setVisibility(View.VISIBLE);
        }
    }
}
