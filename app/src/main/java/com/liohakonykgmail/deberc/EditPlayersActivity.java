package com.liohakonykgmail.deberc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by lioha on 18.01.17.
 */

public class EditPlayersActivity extends SingleFragmentActivity implements EditPlayersFragment.Callbacks {
    @Override
    protected Fragment createFragment() {
        return new EditPlayersFragment();
    }

    @Override
    protected String getTAG() {
        return EditPlayersFragment.EDIT_PLAYERS_TAG;
    }

    @Override
    protected void sendArguments(Fragment fragment) {

    }

   /* @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    public void reciveAndSendDataToDebercActivity(){
        Intent intent  = getIntent();
        int gameWinPoints = intent.getIntExtra(GlobalGame.EXTRA_GAME_WIN_POINTS, 0);
        String name1 = intent.getStringExtra(EditPlayersFragment.EXTRA_PLAYER1);
        String name2 = intent.getStringExtra(EditPlayersFragment.EXTRA_PLAYER2);
        String name3 = intent.getStringExtra(EditPlayersFragment.EXTRA_PLAYER3);
        String name4 = intent.getStringExtra(EditPlayersFragment.EXTRA_PLAYER4);

        Intent intent1 = new Intent(this, DebercActivity.class);
        intent1.putExtra(GlobalGame.EXTRA_GAME_WIN_POINTS, gameWinPoints);
        intent1.putExtra(EditPlayersFragment.EXTRA_PLAYER1, name1);
        intent1.putExtra(EditPlayersFragment.EXTRA_PLAYER2, name2);
        intent1.putExtra(EditPlayersFragment.EXTRA_PLAYER3, name3);
        intent1.putExtra(EditPlayersFragment.EXTRA_PLAYER4, name4);
        startActivity(intent1);
    }*/

    @Override
    public void onGameStarted(GlobalGame globalGame) {
        //if(getSupportFragmentManager().findFragmentByTag(DebercFragment.DEBERC_FRAGMENT_TAG) == null) {
            Intent intent = new Intent(this, DebercActivity.class);
            intent.putExtra(GlobalGame.EXTRA_GAME_WIN_POINTS, globalGame.getGameWinPoints());
            intent.putExtra(EditPlayersFragment.EXTRA_PLAYER1, globalGame.getmTeam1().getmGamer1().getName());
            intent.putExtra(EditPlayersFragment.EXTRA_PLAYER2, globalGame.getmTeam1().getmGamer2().getName());
            intent.putExtra(EditPlayersFragment.EXTRA_PLAYER3, globalGame.getmTeam2().getmGamer1().getName());
            intent.putExtra(EditPlayersFragment.EXTRA_PLAYER4, globalGame.getmTeam2().getmGamer2().getName());
            startActivity(intent);
       /* }else{
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            DebercFragment fragment = new DebercFragment();
        }*/

    }
}
