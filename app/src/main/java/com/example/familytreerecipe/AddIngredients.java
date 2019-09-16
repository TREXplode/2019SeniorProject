package com.example.familytreerecipe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AddIngredients extends AppCompatActivity {
    ArrayList<String> IngredList = new ArrayList<String>();
    String RecName;
    //int buttonpress = 0;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredients);
        Bundle extra = getIntent().getExtras();

        if(extra != null){
            RecName = extra.getString("RecipeName");
            TextView textView = findViewById(R.id.RecipeName);
            textView.setText(RecName);
        }
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                IngredList);
        ListView IngredView = (ListView) findViewById(R.id.IngredList);

        IngredView.setAdapter(adapter);
    }
    public void AddIngredient(View view){
        EditText editText = (EditText) findViewById(R.id.IngredName);
        String Ingred = editText.getText().toString();
        //IngredList.add(Ingred);
        adapter.add(Ingred);
        //IngredList.add(Ingred);
        //adapter.notifyDataSetChanged();
    }
    public void  AddSteps(View view){
        Intent intent = new Intent(this, RecipeStepsActivity.class);
        //TextView TextView = (TextView) findViewById(R.id.RecipeName);
        //String RecName = TextView.toString();
        //ListView IngredView = (ListView) findViewById(R.id.IngredList);

        intent.putExtra("RecipeName", RecName);
        intent.putExtra("Ingredients", IngredList);
        startActivity(intent);
    }
}
