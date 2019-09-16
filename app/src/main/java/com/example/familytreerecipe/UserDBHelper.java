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
import android.widget.ArrayAdapter;

public class UserDBHelper extends SQLiteOpenHelper {

    public final static String DATABASE_NAME = "SeniorProjectUser.db";
    public final static String USER_TABLE_NAME = "Accounts";
    public final static String USER_COLUMN_UNAME ="name";
    public final static String USER_COLUMN_ID = "id";
    public final static String USER_COLUMN_PASSWORD = "password";

    public static final String CONNECTION_TABLE_NAME = "Connections";
    public static final String CONNECTION_COLUMN_ID = "id";
    public static final String CONNECTION_COLUMN_UIDF = "user";
    public static final String CONNECTION_COLUMN_UIDS = "friend";

    public static final String REQUEST_TABLE_NAME = "Requests";
    public static final String REQUEST_COLUMN_ID = "id";
    public static final String REQUEST_COLUMN_UIDF = "user";
    public static final String REQUEST_COLUMN_UIDS = "friend";

    public static final String CREATE_USERTABLE = "CREATE TABLE " + USER_TABLE_NAME
            + " (" + USER_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + USER_COLUMN_UNAME + " TEXT, "
            + USER_COLUMN_PASSWORD + " TEXT)";

    public static final String CREATE_CONNECTIONTABLE = "CREATE TABLE " + CONNECTION_TABLE_NAME
            + " (" + CONNECTION_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CONNECTION_COLUMN_UIDF + " INTEGER, "
            + CONNECTION_COLUMN_UIDS + " INTEGER);";

    public static final String CREATE_REQUESTTBALE = "CREATE TABLE " + REQUEST_TABLE_NAME
            + " (" + REQUEST_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + REQUEST_COLUMN_UIDF + " INTEGER, "
            + REQUEST_COLUMN_UIDS + " INTEGER);";

    public UserDBHelper(Context context){
        super(context, DATABASE_NAME, null, 2);
        //context.deleteDatabase(DATABASE_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        //db.execSQL("DROP TABLE IF EXISTS " + CONNECTION_TABLE_NAME);
        //db.execSQL("DROP TABLE IF EXISTS " + REQUEST_TABLE_NAME);
        db.execSQL(CREATE_USERTABLE);
        db.execSQL(CREATE_CONNECTIONTABLE);
        db.execSQL(CREATE_REQUESTTBALE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CONNECTION_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + REQUEST_TABLE_NAME);
        onCreate(db);
    }
    public void AddUser(String name, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_COLUMN_UNAME, name);
        values.put(USER_COLUMN_PASSWORD, password);
        db.insert(USER_TABLE_NAME, null, values);
    }

    public void AddConnection(int UID, int FID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CONNECTION_COLUMN_UIDF, UID);
        values.put(CONNECTION_COLUMN_UIDS, FID);
        db.insert(CONNECTION_TABLE_NAME, null, values);
    }

    public void NewRequest(int uid, int rid){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(REQUEST_COLUMN_UIDF, uid);
        values.put(REQUEST_COLUMN_UIDS, rid);
        db.insert(REQUEST_TABLE_NAME, null, values);
    }

    public void DeleteRequest(int uid, int fid){
        SQLiteDatabase db = this.getReadableDatabase();
        int rid = 0;
        Cursor res = db.rawQuery("SELECT * FROM " + REQUEST_TABLE_NAME + " WHERE " + REQUEST_COLUMN_UIDS + "=" + fid + "", null);
        if(res.getCount() > 0){
            res.moveToFirst();
            while(!res.isAfterLast() && rid == 0){
                if(uid == res.getInt(res.getColumnIndex(REQUEST_COLUMN_UIDF))){
                    rid = res.getInt(res.getColumnIndex(REQUEST_COLUMN_ID));
                }
                res.moveToNext();
            }
            db = this.getWritableDatabase();
            db.delete(REQUEST_TABLE_NAME, REQUEST_COLUMN_ID + "=" + rid, null);
            //db.execSQL("DELETE * FROM " + REQUEST_TABLE_NAME + " WHERE " + REQUEST_COLUMN_ID + "=" + rid + ";", null);
        }

    }

    public void DeleteAllRequests(int uid){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(REQUEST_TABLE_NAME, REQUEST_COLUMN_UIDS + "=" + uid, null);
    }

    public ArrayList<String> GetAllRequests(int uid){
        ArrayList<String> requests = new ArrayList<>();
        ArrayList<Integer> requestids = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res  = db.rawQuery("SELECT * FROM " + REQUEST_TABLE_NAME + " WHERE " + REQUEST_COLUMN_UIDS + "=" + uid + "", null);
        res.moveToFirst();
        while(!res.isAfterLast()){
            requestids.add(res.getInt(res.getColumnIndex(REQUEST_COLUMN_UIDF)));
            res.moveToNext();
        }
        res = db.rawQuery("SELECT * FROM " + USER_TABLE_NAME + "", null);
        res.moveToFirst();
        while(!res.isAfterLast()){
            if(requestids.contains(res.getInt(res.getColumnIndex(USER_COLUMN_ID)))){
                requests.add(res.getString(res.getColumnIndex(USER_COLUMN_UNAME)));
            }
            res.moveToNext();
        }
        return requests;
    }

    public ArrayList<String> GetConnections(int UID){
        ArrayList<String> friends = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + CONNECTION_TABLE_NAME + "", null);

        ArrayList<Integer> friendids = new ArrayList<>();
        res.moveToFirst();
        while(!res.isAfterLast()){
            if(UID == res.getInt(res.getColumnIndex(CONNECTION_COLUMN_UIDF)))
                friendids.add(res.getInt(res.getColumnIndex(CONNECTION_COLUMN_UIDS)));
            res.moveToNext();
        }
        if(friendids.size() > 0){
            res = db.rawQuery("SELECT * FROM Accounts", null);
            res.moveToFirst();
            while(!res.isAfterLast()){
                if(friendids.contains(res.getInt(res.getColumnIndex(USER_COLUMN_ID)))){
                    friends.add(res.getString(res.getColumnIndex(USER_COLUMN_UNAME)));
                }
                res.moveToNext();
            }
        }
        return friends;
    }

    public ArrayList<String> GetNonFriends(int uid){
        ArrayList<String> nonfriends  = new ArrayList<>();
        ArrayList<Integer> friendids = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        //int cid = 0;
        Cursor res = db.rawQuery("SELECT * FROM Connections", null);
        friendids.add(uid);
        res.moveToFirst();
        while(!res.isAfterLast()){
            if(uid == res.getInt(res.getColumnIndex(CONNECTION_COLUMN_UIDF)))
                friendids.add(res.getInt(res.getColumnIndex(CONNECTION_COLUMN_UIDS)));
            res.moveToNext();
        }
        res = db.rawQuery("SELECT * FROM Accounts", null);
        res.moveToFirst();
        while(!res.isAfterLast()){
            if(!friendids.contains(res.getInt(res.getColumnIndex(USER_COLUMN_ID)))){
                nonfriends.add(res.getString(res.getColumnIndex(USER_COLUMN_UNAME)));
            }
            res.moveToNext();
        }

        return nonfriends;
    }

    public boolean CheckPassword(String name, String password){
        boolean correct = false;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("SELECT * FROM " + USER_TABLE_NAME, null);
        if(res.getCount() > 0){
            res.moveToFirst();
            for(int i = 0; i < res.getCount(); i++){
                if(name.contentEquals(res.getString(res.getColumnIndex(USER_COLUMN_UNAME)))){
                    if(password.contentEquals(res.getString(res.getColumnIndex(USER_COLUMN_PASSWORD))))
                        correct = true;
                }
                res.moveToNext();
            }
        }
        else
        {
            correct = false;
        }

        return correct;
    }

    public int GetUserId(String username){
        int uid = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("SELECT * FROM " + USER_TABLE_NAME, null);
        //if(res.getCount() > 0){
            res.moveToFirst();
            for(int i = 0; i < res.getCount(); i++){
                if(username.contentEquals(res.getString(res.getColumnIndex(USER_COLUMN_UNAME)))){
                    uid = res.getInt(res.getColumnIndex((USER_COLUMN_ID)));
                }
                res.moveToNext();
            }

        return uid;
    }

    public boolean CheckUserName(String name){
        boolean correct = true;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("SELECT * FROM " + USER_TABLE_NAME, null);
        if(res.getCount() <= 0){
            correct = true;
        }
        else {
            res.moveToFirst();
            for (int i = 0; i < res.getCount(); i++) {
                if (res.getString(res.getColumnIndex(USER_COLUMN_UNAME)) == name)
                    correct = false;
                res.moveToNext();
            }
        }
        return correct;
    }
}
