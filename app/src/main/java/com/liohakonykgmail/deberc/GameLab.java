package com.liohakonykgmail.deberc;


import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by lioha on 18.01.17.
 */

public class GameLab {
    private ArrayList<GlobalGame> games;
    private Context myAppContext;
    private static GameLab gameLab;

    private GameLab(Context myAppContext){
        this.myAppContext = myAppContext;
    }
    public static GameLab get(Context c){
        if(gameLab == null){
            gameLab = new GameLab(c);
        }
        return gameLab;
    }
    public GlobalGame getGlobalGame(UUID uuid){

        for(GlobalGame g:games){
            if(g.getmId().equals(uuid)){
                return g;
            }
        }
        return null;
    }
}
