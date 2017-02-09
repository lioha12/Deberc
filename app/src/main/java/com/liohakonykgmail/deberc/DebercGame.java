package com.liohakonykgmail.deberc;

import java.util.ArrayList;

/**
 * Created by lioha on 17.01.17.
 */

public class DebercGame {

    public static final String[] gamePoints = new String[]{"", "162", "182", "202", "212", "222", "232", "242",
            "252", "262", "272", "282", "292", "302"};

    private int currentGame;
    private int calculatedPoints;
    private int otherPoints;

    public DebercGame(int currentGame, int calculatedPoints){
        this.currentGame = currentGame;
        this.calculatedPoints = calculatedPoints;
    }

    public int gameResult(DebercGame game) {
        if(game.currentGame != 0&&game.calculatedPoints != 0){
            game.otherPoints = game.currentGame - game.calculatedPoints;
            return game.otherPoints;
        }else return 0;
    }

    public void setCurrentGame(int currentGame) {
        this.currentGame = currentGame;
    }

    public void setCalculatedPoints(int calculatedPoints){
        if(calculatedPoints > currentGame){
            return;
        }else
        this.calculatedPoints = calculatedPoints;
    }

    public void setOtherPoints(int otherPoints) {
        this.otherPoints = otherPoints;
    }
}
