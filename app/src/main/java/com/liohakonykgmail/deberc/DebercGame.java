package com.liohakonykgmail.deberc;


import android.util.Log;

/**
 * Created by lioha on 17.01.17.
 */

public class DebercGame {

    public static final String TEAM1_FLAG = "t1";
    public static final String TEAM2_FLAG = "t2";

    static final String LOG_DG = "de";

    public static final String[] gamePoints = new String[]{"", "162", "182", "202", "212", "222", "232", "242",
            "252", "262", "272", "282", "292", "302"};

    private int currentGame;
    private int pointsTeam1;
    private int pointsTeam2;

    public DebercGame(int currentGame){
        this.currentGame = currentGame;
        //this.calculatedPoints = calculatedPoints;
    }

    public int gameResult(DebercGame game, String teamFlag) {
        int result = 0;
        if(game.currentGame != 0){
            if(teamFlag.equals(TEAM2_FLAG)) {
                game.pointsTeam2 = game.currentGame - game.pointsTeam1;
                result = game.pointsTeam2;
                Log.d(LOG_DG, "t1 = " + game.pointsTeam1 + "; t2 = " +
                        game.pointsTeam2);
            }else if(teamFlag.equals(TEAM1_FLAG)){
                game.pointsTeam1 = game.currentGame - game.pointsTeam2;
                result = game.pointsTeam1;
                Log.d(LOG_DG, "2t1 = " + game.pointsTeam1 + "; t2 = " +
                        game.pointsTeam2);
            }
        } else result = 0;
        Log.d(LOG_DG, "3t1 = " + game.pointsTeam1 + "; t2 = " +
                game.pointsTeam2);
        return result;

    }

    public void setCurrentGame(int currentGame) {
        this.currentGame = currentGame;
    }

    public void setPointsTeam1(int points){

        if(points > currentGame){
            return;
        }else
        this.pointsTeam1 = points;
    }

    public void setPointsTeam2(int points) {

        if(points > currentGame){
            return;
        }
        this.pointsTeam2 = points;
    }
}
