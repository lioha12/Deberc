package com.liohakonykgmail.deberc;

import android.content.Context;

import java.util.Date;
import java.util.UUID;

/**
 * Created by lioha on 17.01.17.
 */

public class GlobalGame {



    public static final String EXTRA_GAME_WIN_POINTS = "game_win_points";
    private int gameWinPoints;
    private int team1Points = 0;
    private int team2Points = 0;

    private TeamOfGamer mTeam1;
    private TeamOfGamer mTeam2;

    private Context appContext;
    private UUID mId;
    private Date mDate;



    public GlobalGame (TeamOfGamer team1,
                       TeamOfGamer team2, int gameWinPoints){
        this.mTeam1 = team1;
        this.mTeam2 = team2;
        this.gameWinPoints = gameWinPoints;

        mId = UUID.randomUUID();
        mDate = new Date(System.currentTimeMillis());
    }

    public int getGameWinPoints() {
        return gameWinPoints;
    }

    public void setGameWinPoints(int gameWinPoints) {
        this.gameWinPoints = gameWinPoints;
    }

    public UUID getmId() {
        return mId;
    }

    public void setmTeam1(TeamOfGamer mTeam1) {
        this.mTeam1 = mTeam1;
    }

    public void setmTeam2(TeamOfGamer mTeam2) {
        this.mTeam2 = mTeam2;
    }
    public TeamOfGamer getmTeam1() {
        return mTeam1;
    }

    public TeamOfGamer getmTeam2() {
        return mTeam2;
    }

    public void setTeam1Points(int newPoints){
        team1Points = team1Points + newPoints;
        if(team1Points >= gameWinPoints){
            mTeam1.setWin(true);
        }
    }

    public void setTeam2Points(int newPoints){
        team2Points = team2Points + newPoints;
        if(team1Points >= gameWinPoints){
            mTeam2.setWin(true);
        }
    }

    public int getTeam1Points() {
        return team1Points;
    }

    public int getTeam2Points() {
        return team2Points;
    }
}
