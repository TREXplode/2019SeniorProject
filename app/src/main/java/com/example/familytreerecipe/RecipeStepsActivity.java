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

public class RecipeStepsActivity extends AppCompatActivity {

    ArrayList<String> StepsList = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            String value = extras.getString("RecipeName");
            TextView textView = findViewById(R.id.RecipeName);
            textView.setText(value);
        }

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                StepsList);
        ListView StepsView = (ListView) findViewById(R.id.StepsList);

        StepsView.setAdapter(adapter);
    }

    public void AddStepBtn(View view)
    {
        EditText editText = (EditText) findViewById(R.id.Instructions);

        String Step = editText.getText().toString();
        //IngredList.add(Steps);
        adapter.add(Step);
        //StepsList.add(Step);
        //adapter.notifyDataSetChanged();
    }

    public void FinishBtn(View view)
    {
        Intent intent = new Intent(this, CreateRecipeFinal.class);
        Bundle extras = getIntent().getExtras();
        ArrayList<String> IngredList =  extras.getStringArrayList("Ingredients");
        String RecipeName = extras.getString("RecipeName");

        intent.putExtra("RecipeName", RecipeName);
        intent.putExtra("Ingredients", IngredList);
        intent.putExtra("Steps", StepsList);
        startActivity(intent);
    }

}
