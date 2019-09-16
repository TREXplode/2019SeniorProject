package com.example.familytreerecipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "SeniorProject.db";

    public static final String RECIPE_TABLE_NAME = "Recipes";
    public static final String RECIPE_COLUMN_ID = "id";
    public static final String RECIPE_COLUMN_NAME = "name";
    public static final String RECIPE_COLUMN_USER_ID = "user_id";

    public static final String INGREDIENT_TABLE_NAME = "Ingredients";
    public static final String INGREDIENT_COLUMN_ID = "id";
    public static final String INGREDIENT_COLUMN_NAME = "name";
    public static final String INGREDIENT_COLUMN_RECIPE_ID = "recipe_id";

    public static final String INSTRUCTIONS_TABLE_NAME = "Instructions";
    public static final String INSTRUCTIONS_COLUMN_ID = "id";
    public static final String INSTRUCTIONS_COLUMN_STEP = "step";
    public static final String INSTRUCTIONS_COLUMN_RECIPE_ID = "recipe_id";

    private static final String CREATE_RECIPE = "CREATE TABLE " + RECIPE_TABLE_NAME
            + " (" + RECIPE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + RECIPE_COLUMN_NAME
            + " TEXT, " + RECIPE_COLUMN_USER_ID + " INTEGER)";
    private static final String CREATE_INGREDIENTS = "CREATE TABLE " + INGREDIENT_TABLE_NAME
            + " (" + INGREDIENT_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + INGREDIENT_COLUMN_NAME
            + " TEXT, "
            + INGREDIENT_COLUMN_RECIPE_ID + " INTEGER, "
            + "FOREIGN KEY (" + INGREDIENT_COLUMN_RECIPE_ID
            + ") REFERENCES " + RECIPE_TABLE_NAME + " (" + RECIPE_COLUMN_ID + "))";
    private static final String CREATE_INSTRUCTIONS = "CREATE TABLE " + INSTRUCTIONS_TABLE_NAME
            + " (" + INSTRUCTIONS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + INSTRUCTIONS_COLUMN_STEP
            + " TEXT, "
            + INSTRUCTIONS_COLUMN_RECIPE_ID + " INTEGER, "
            + "FOREIGN KEY (" +INSTRUCTIONS_COLUMN_RECIPE_ID
            + ") REFERENCES " + RECIPE_TABLE_NAME + " (" + RECIPE_COLUMN_ID + "))";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
        //context.deleteDatabase(DATABASE_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_RECIPE);
        db.execSQL(CREATE_INGREDIENTS);
        db.execSQL(CREATE_INSTRUCTIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS Recipes");
        db.execSQL("DROP TABLE IF EXISTS Ingredients");
        db.execSQL("DROP TABLE IF EXISTS Instructions");
        onCreate(db);
    }

    public boolean InsertRecipe(String name, List<String> Ingredients, List<String> Steps, int userid){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues RecipeValues = new ContentValues();
        RecipeValues.put(RECIPE_COLUMN_NAME, name);
        RecipeValues.put(RECIPE_COLUMN_USER_ID, userid);
        int recipeid = (int) db.insert(RECIPE_TABLE_NAME, null, RecipeValues);

        //Cursor res = db.rawQuery("SELECT * FROM Recipe WHERE name='name'", null);
        //res.moveToFirst();

        ContentValues IngredValues = new ContentValues();
        IngredValues.put(INGREDIENT_COLUMN_RECIPE_ID, recipeid);
        for(int i = 0; i < Ingredients.size(); i++){
            //IngredValues = new ContentValues();
            IngredValues.put(INGREDIENT_COLUMN_RECIPE_ID, recipeid);
            IngredValues.put(INGREDIENT_COLUMN_NAME, Ingredients.get(i));
            db.insert(INGREDIENT_TABLE_NAME, null, IngredValues);
        }
        ContentValues StepsValues = new ContentValues();
        StepsValues.put(INSTRUCTIONS_COLUMN_RECIPE_ID, recipeid);
        for(int i = 0; i < Steps.size(); i++){
            //StepsValues = new ContentValues();
            StepsValues.put(INSTRUCTIONS_COLUMN_RECIPE_ID, recipeid);
            StepsValues.put(INSTRUCTIONS_COLUMN_STEP, Steps.get(i));
            db.insert(INSTRUCTIONS_TABLE_NAME, null, StepsValues);
        }
        return true;
    }

    public String GetRecipe(int recipeid){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM Recipes WHERE id="+recipeid+"", null);
        res.moveToFirst();
        String name = res.getString(res.getColumnIndex(RECIPE_COLUMN_NAME));
        return name;
    }

    public ArrayList<String> GetAllRecipe(){
        ArrayList<String> RecipeNames = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM Recipes", null);
        res.moveToFirst();
        while(res.isAfterLast() == false){
            RecipeNames.add(res.getString(res.getColumnIndex(RECIPE_COLUMN_NAME)));
            res.moveToNext();
        }
        return RecipeNames;
    }
    public ArrayList<String> GetSteps(int recipeid){
        ArrayList<String> steps = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM Instructions "
                + "WHERE "+ INSTRUCTIONS_COLUMN_RECIPE_ID + " =" + recipeid +""
                , null);
        res.moveToFirst();
        int testint = 0;
        //testint = res.getInt(res.getColumnIndex(INSTRUCTIONS_COLUMN_RECIPE_ID));
        while(res.isAfterLast() == false){
            steps.add(res.getString(res.getColumnIndex(INSTRUCTIONS_COLUMN_STEP)));
            //testint = res.getInt(res.getColumnIndex(INSTRUCTIONS_COLUMN_RECIPE_ID));
            res.moveToNext();
        }
        return steps;
    }
    public ArrayList<String> GetIngredients(int recipeid) {
        ArrayList<String> ingredients = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM Ingredients"
                + " WHERE " + INGREDIENT_COLUMN_RECIPE_ID +" =" + recipeid + ""
                , null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            ingredients.add(res.getString(res.getColumnIndex(INGREDIENT_COLUMN_NAME)));
            res.moveToNext();
        }
        return ingredients;
    }
}
