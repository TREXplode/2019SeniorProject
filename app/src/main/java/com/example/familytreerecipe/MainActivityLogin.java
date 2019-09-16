package com.example.familytreerecipe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivityLogin extends AppCompatActivity {
    //Button LogOutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        //LogOutBtn = (Button) findViewById(R.id.MainLOBtn);
        //SetLogOut();
    }
    public void LCreateRecipe(View view){
        Intent intent = new Intent(this, CreateRecipe.class);
        startActivity(intent);
    }
    public void LViewRecipes(View view){
        Intent intent = new Intent(this, RecipeList.class);
        startActivity(intent);
    }
    public void LogOut(View view){
                    //LogOutBtn.setText("Logout");
                    MainActivity.Userid = 0;
                    MainActivity.logged_in = false;
                    Intent intent = new Intent(MainActivityLogin.this, MainActivity.class);
                    startActivity(intent);
    }
    public void FriendList(View view) {
        Intent intent = new Intent(this, ViewConnections.class);
        startActivity(intent);
    }
}
