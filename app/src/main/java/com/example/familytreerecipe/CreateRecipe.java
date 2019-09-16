package com.example.familytreerecipe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.List;

public class CreateRecipe extends AppCompatActivity {
    //public static final String RecipeName = "com.example.familytreerecipe.CreateRecipe";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);
    }

    public void CreateNext(View view) {
        Intent intent = new Intent(this, AddIngredients.class);
        EditText editText = (EditText) findViewById(R.id.NAME);
        String message = editText.getText().toString();
        intent.putExtra("RecipeName", message);
        startActivity(intent);
    }
}
