package com.example.familytreerecipe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    DBHelper mydb;
    public static boolean logged_in = false;
    public static int Userid;
    Button LogBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mydb = new DBHelper(this);

        LogBtn = (Button) findViewById(R.id.MainLogBtn);
        if(!logged_in){
            SetLogin();
        }
    }


    public void CreateRecipe(View view){
        Intent intent = new Intent(this, CreateRecipe.class);
        startActivity(intent);
    }
    public void ViewRecipes(View view){
        Intent intent = new Intent(this, RecipeList.class);
        startActivity(intent);
    }
    public void SetLogin(){
        LogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    LogBtn.setText("Logout");
                    Intent intent = new Intent(MainActivity.this, Login.class);
                    startActivity(intent);
                }
            }
        } );
    }
}
