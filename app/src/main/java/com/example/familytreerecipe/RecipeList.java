package com.example.familytreerecipe;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class RecipeList extends AppCompatActivity {
    DBHelper myDB;

    ArrayList<String> RecipesList;
    ArrayAdapter RecipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        myDB = new DBHelper(this);

        ListView Recipes = (ListView) findViewById(R.id.RecipeList);
        RecipesList = myDB.GetAllRecipe();
        RecipeAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        Recipes.setAdapter(RecipeAdapter);
        RecipeAdapter.clear();
        RecipeAdapter.addAll(RecipesList);

        Recipes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String selectedrecipe = (String) parent.getItemAtPosition(position);
                int id_to_search = position + 1;
                Bundle databundle = new Bundle();
                databundle.putInt("id", id_to_search);

                Intent intent = new Intent(getApplicationContext(), RecipeView.class);

                intent.putExtras(databundle);
                startActivity(intent);
            }
        });
    }


}
