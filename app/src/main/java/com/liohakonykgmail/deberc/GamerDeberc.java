package com.liohakonykgmail.deberc;

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
    public static ArrayList<String> getGamersName()
    {
            ArrayList<String> list = new ArrayList<>();
            for (GamerDeberc g : getGamers()) {
                if(g.name == "")
                {
                    list.add("");
                }else
                    list.add(g.name);
            }
            return list;
    }


}
