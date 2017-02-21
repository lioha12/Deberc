package com.liohakonykgmail.deberc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by lioha on 17.01.17.
 */

public class GamerDeberc {
    private static ArrayList<GamerDeberc> gamers = new ArrayList<>();
    private String name;
    //private UUID mId;

    public GamerDeberc(String name)
    {
        this.name = name;

        //this.mId = UUID.randomUUID();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static ArrayList<GamerDeberc> getGamers() {
        if(gamers.size() == 0){
            gamers = new ArrayList<>();
            gamers.add(new GamerDeberc(""));
        }
        return gamers;
    }

    public static void loadGamers(GamersDBHelper dbHelper){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        gamers = new ArrayList<>();
        gamers.add(new GamerDeberc(""));
        Cursor c = db.query("gamerstable", null, null, null, null, null, null);
        if(c.moveToFirst()){
            int nameColIndex = c.getColumnIndex("name");
            do {
                gamers.add(new GamerDeberc(c.getString(nameColIndex)));
            }while (c.moveToNext());
        }
        db.close();
    }

    public static ArrayList<String> getGamersName()
    {
            ArrayList<String> list = new ArrayList<>();
            for (GamerDeberc g : getGamers()) {
                    list.add(g.name);
            }
            return list;
    }

    public static void addGamers(GamerDeberc gd, GamersDBHelper dbHelper){
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        cv.put("name", gd.getName());
        db.insert("gamerstable", null, cv);
        GamerDeberc.gamers.add(gd);
        db.close();
    }

    public static void deleteGamer(String name, GamersDBHelper dbHelper){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("gamerstable", "name = " + name, null);

        gamers = null;                                                                       //update gamers content
        gamers = new ArrayList<>();
        gamers.add(new GamerDeberc(""));
        Cursor c = db.query("gamerstable", null, null, null, null, null, null);
        if(c.moveToFirst()){
            int nameColIndex = c.getColumnIndex("name");
            do {
                gamers.add(new GamerDeberc(c.getString(nameColIndex)));
            }while (c.moveToNext());
        }
        db.close();
    }
    public static void cleanDBGamers(GamersDBHelper dbHelper){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("gamerstable", null, null);
        gamers = new ArrayList<>();
        gamers.add(new GamerDeberc(""));
    }

    public static class GamersDBHelper extends SQLiteOpenHelper{

        public GamersDBHelper(Context context){
            super(context, "dbForGamers", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table gamerstable("
            + "id integer primary key autoincrement,"
            + "name text" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
