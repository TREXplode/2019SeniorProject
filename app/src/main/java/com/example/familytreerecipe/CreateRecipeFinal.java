package com.example.familytreerecipe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.support.v7.recyclerview.extensions.ListAdapter;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CreateRecipeFinal extends AppCompatActivity {
    String RecipeName;
    ArrayList<String> IngList;
    ArrayList<String> StepList;
    private DBHelper myDB;
    //this value will change once user accounts are implemented
    int userid = MainActivity.Userid;

    ArrayAdapter stepadapter;
    ArrayAdapter ingadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe_final);

        myDB = new DBHelper(this);

        Bundle extras = getIntent().getExtras();

        RecipeName = extras.getString("RecipeName");
        IngList = extras.getStringArrayList("Ingredients");
        StepList = extras.getStringArrayList("Steps");

        ListView StepsView = (ListView) findViewById(R.id.StepList) ;
        stepadapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        stepadapter.clear();
        StepsView.setAdapter(stepadapter);

        stepadapter.addAll(StepList);

        ListView IngView = (ListView) findViewById(R.id.IngList);
        ingadapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        ingadapter.clear();
        IngView.setAdapter(ingadapter);

        ingadapter.addAll(IngList);

        TextView NameView = (TextView) findViewById(R.id.RecipeName);

        NameView.setText(RecipeName);

    }
    public void SaveRecipe(View view)
    {
        //send database to data class
        myDB.InsertRecipe(RecipeName, IngList, StepList, userid);
        //Return to main activity
        if(MainActivity.logged_in){
            Intent intent = new Intent(this, MainActivityLogin.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }


}
