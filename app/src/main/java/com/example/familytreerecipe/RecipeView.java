package com.example.familytreerecipe;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecipeView extends AppCompatActivity {
    DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_view);

        mydb = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            int val = extras.getInt("id");

            String RecName = mydb.GetRecipe(val);
            ArrayList<String> Steps = mydb.GetSteps(val);
            ArrayList<String> Ingreds = mydb.GetIngredients(val);

            ListView StepsView = (ListView) findViewById(R.id.StepsListView);
            ListView IngredsView = (ListView) findViewById(R.id.IngredientsListView);
            TextView RecipeName = (TextView) findViewById(R.id.RecipeName);

            ArrayAdapter StepAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, Steps);
            ArrayAdapter IngredAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, Ingreds);

            StepsView.setAdapter(StepAdapter);
            IngredsView.setAdapter(IngredAdapter);

            RecipeName.setText(RecName);
        }
    }

}
