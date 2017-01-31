package com.liohakonykgmail.deberc;

/**
 * Created by lioha on 18.01.17.
 */

public class TeamOfGamer {
    private GamerDeberc mGamer1;
    private GamerDeberc mGamer2;

    public TeamOfGamer(GamerDeberc gamer1, GamerDeberc gamer2){
        this.mGamer1 = gamer1;
        this.mGamer2 = gamer2;
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
}
