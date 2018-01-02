package com.liohakonykgmail.deberc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by lioha on 18.01.17.
 */

public class TeamOfGamer {
    private static ArrayList<TeamOfGamer> teams = new ArrayList<>();
    private GamerDeberc mGamer1;
    private GamerDeberc mGamer2;
    private int mId;
    private int mPoints;
    private boolean isWin;

    private static int maxIdT;

    public TeamOfGamer(GamerDeberc gamer1, GamerDeberc gamer2){
        this.mGamer1 = gamer1;
        this.mGamer2 = gamer2;
    }

    public static ArrayList<TeamOfGamer> getTeams() {
        return teams;
    }

    public int getmPoints() {
        return mPoints;
    }

    public void setmPoints(int mPoints) {
        this.mPoints = mPoints;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public boolean isWin() {
        return isWin;
    }

    public void setWin(boolean win) {
        isWin = win;
    }

    public GamerDeberc getmGamer1() {
        return mGamer1;
    }

    public GamerDeberc getmGamer2() {
        return mGamer2;
    }

    public String getNameOfTeam(){
        return mGamer1.getName() + "/" + mGamer2.getName();
    }

    public int getId() {
        return mId;
    }

    public static void addTeam(TeamOfGamer teamOfGamer, TeamsDBHelper dbHelper){
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        final SQLiteStatement sqLiteStatement = db.compileStatement("SELECT MAX(id) FROM teamstable");
        maxIdT = ((int)sqLiteStatement.simpleQueryForLong()) + 1;
        teamOfGamer.setmId(maxIdT);

        cv.put("id", teamOfGamer.getId());
        cv.put("gamer1", teamOfGamer.getmGamer1().getmId());
        cv.put("gamer2", teamOfGamer.getmGamer2().getmId());
        cv.put("points", 0);


        db.insert("teamstable", null, cv);
        teams.add(teamOfGamer);
        db.close();

        //Log.d("debe", "ADD_TEAM - " + "maxId = " + maxIdT + "id = " + teamOfGamer.getId());
    }

    public static void loadTeams(TeamsDBHelper dbHelper){
        GamerDeberc gamer1 = null;
        GamerDeberc gamer2 = null;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        teams = new ArrayList<>();
        Cursor c = db.query("teamstable", null, null, null, null, null, null);
        if(c.moveToFirst()){
            int idColumnIndex = c.getColumnIndex("id");
            int columnIndex1 = c.getColumnIndex("gamer1");
            int columnIndex2 = c.getColumnIndex("gamer2");
            int pointsColumnIndex = c.getColumnIndex("points");
            do {
                int gamer1Id = c.getInt(columnIndex1);
                int gamer2Id = c.getInt(columnIndex2);
                int teamPoints = c.getInt(pointsColumnIndex);
                for(GamerDeberc gd:GamerDeberc.getGamers()){
                    int gdId = gd.getmId();
                    if(gdId == gamer1Id){
                        gamer1 = gd;
                    }
                    if(gdId == gamer2Id){
                        gamer2 = gd;
                    }
                }
                TeamOfGamer teamOfGamer = new TeamOfGamer(gamer1, gamer2);
                teamOfGamer.setmId(c.getInt(idColumnIndex));
                teamOfGamer.setmPoints(teamPoints);
                teams.add(teamOfGamer);
                Log.d("debe", "current team id - " + teamOfGamer.getId() + "\n" +
                "g1Id = " + gamer1.getmId() + "\n"+
                "g2Id = " + gamer2.getmId() + "\n"+
                "points = " + teamPoints);
            }while (c.moveToNext());
            c.close();
        }
        //Log.d("debe", "teams count = " + teams.size());

        db.close();
    }

    public static void cleanTeams(TeamsDBHelper dbHelper){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("teamstable", null, null);
        teams = new ArrayList<>();
        db.close();
    }

    public static void updateTeamInfo(TeamsDBHelper dbHelper, int id, int points){

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        //Cursor c = null;
        //try {
            /*c = db.query("teamstable", new String[]{ "points"},
                    "id = ?", new String[]{Integer.toString(id)}, null, null, null);
            c.moveToFirst();
            int pointsColumnIndex = c.getColumnIndex("points");
            //
            int pointsOld = c.getInt(pointsColumnIndex);*/
            int pointsOld = 0;
        String sqlStr = "";
            for(int i = 0; i < teams.size(); i++){
                TeamOfGamer team = teams.get(i);
                if (team.getId() == id){
                    pointsOld = team.getmPoints();
                    team.setmPoints(pointsOld + points);
                    teams.set(i, team);    //updating team in teams list
                    sqlStr = "UPDATE teamstable SET points = " + String.valueOf(points + pointsOld) +
                            "WHERE id = " + String.valueOf(id);
                    db.execSQL(sqlStr);
                }
                db.close();
            }
            //cv.put("points", points + pointsOld);
            //db.update("teamstable", cv, "id=" + String.valueOf(id), null);
            Log.d("debe", "teamPoint = " + String.valueOf(points + pointsOld));
       // }catch(Exception e){
         //   Log.d("debe", "catch in TeamOFGamer = " + String.valueOf(String.valueOf(points)));
        //}
        //finally {
            //c.close();
          //  db.close();
        //}

    }

    public static class TeamsDBHelper extends SQLiteOpenHelper{

        public TeamsDBHelper(Context context){
            super(context, "dbForTeams", null, 1);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table teamstable("
                    + "id integer primary key,"
                    + "gamer1 integer,"
                    + "gamer2 integer,"
                    + "points integer"+ ");");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
