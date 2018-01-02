package com.liohakonykgmail.deberc;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.zip.Inflater;

/**
 * Created by lioha on 18.01.17.
 */

public class EditPlayersActivity extends SingleFragmentActivity
        implements EditPlayersFragment.Callbacks, FragmetListTeams.CallbackForTeamList {

    private LinearLayout mLayout;
    private FrameLayout layoutForList;
    public static final String TAG_TEAMS = "which team";
    private int mWhichTeam;

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
    @Override
    protected int getLayoutResId(){
        return R.layout.activity_fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mLayout = (LinearLayout)findViewById(R.id.main_layout);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d("debe", "---onDestroy EditPlayersActivity---");
    }
/*
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

    @Override
    public void onTeam1ChoiceButtonPressed() {
        /*
        if (findViewById(R.id.list_team_fragment_container) == null) {
            Intent intent = new Intent(this, ActivityTeamsList.class);
            startActivityForResult(intent, 1);
        } else {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            Fragment fragment = new FragmetListTeams();
            Fragment editPlayersFragment = fm.findFragmentById(R.id.fragment_container);
            fragment.setTargetFragment(editPlayersFragment, 1);
            ft.add(R.id.list_team_fragment_container, fragment);
            ft.commit();
        }*/

        int screen = getResources().getConfiguration().screenWidthDp;
        mWhichTeam = 1;

        if (screen < 500) {

            Intent intent = new Intent(this, ActivityTeamsList.class);
            intent.putExtra(TAG_TEAMS, 1);
            startActivityForResult(intent, 1);
        } else {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            //LayoutInflater inflater = LayoutInflater.from(this);

            layoutForList = new FrameLayout(this);
            layoutForList.setId(R.id.layoutForList);
            LinearLayout.LayoutParams frameParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT, 2.0f);
            layoutForList.setLayoutParams(frameParams);
            mLayout.addView(layoutForList);

            Fragment listTeamFragment = new FragmetListTeams();
            Fragment editPlayersFragment = fm.findFragmentById(R.id.fragment_container);

            //inflater.inflate(R.layout.activity_two_pane, null);
            listTeamFragment.setTargetFragment(editPlayersFragment, 1);
            //ft.replace(R.id.fragment_container_two, editPlayersFragment);
            ft.add(R.id.layoutForList, listTeamFragment);
            ft.commit();
        }
    }
    @Override
    public void onTeam2ChoiceButtonPressed() {
        mWhichTeam = 2;
        /*if(findViewById(R.id.list_team_fragment_container) == null) {
            Intent intent = new Intent(this, ActivityTeamsList.class);
            startActivityForResult(intent, 2);
        } else{
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            Fragment fragment = new FragmetListTeams();
            Fragment editPlayersFragment = fm.findFragmentById(R.id.fragment_container);
            fragment.setTargetFragment(editPlayersFragment, 2);
            ft.add(R.id.list_team_fragment_container, fragment);
            ft.commit();
        }*/
        int screen = getResources().getConfiguration().screenWidthDp;

        if(screen < 500){
            Intent intent = new Intent(this, ActivityTeamsList.class);
            startActivityForResult(intent, 2);
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        //LayoutInflater inflater = LayoutInflater.from(this);

        layoutForList = new FrameLayout(this);
        layoutForList.setId(R.id.layoutForList);
        LinearLayout.LayoutParams frameParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, 2.0f);
        layoutForList.setLayoutParams(frameParams);
        mLayout.addView(layoutForList);

        Fragment listTeamFragment = new FragmetListTeams();
        Fragment editPlayersFragment = fm.findFragmentById(R.id.fragment_container);

        //inflater.inflate(R.layout.activity_two_pane, null);
        listTeamFragment.setTargetFragment(editPlayersFragment, 2);
        //ft.replace(R.id.fragment_container_two, editPlayersFragment);
        ft.add(R.id.layoutForList, listTeamFragment);
        ft.commit();
    }

    @Override
    public void onTeamSelected(int idTeam) {
        Intent intent = new Intent();
        intent.putExtra(FragmetListTeams.TEAM_EXTRA, idTeam);
        setResult(RESULT_OK, intent);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        fragment.onActivityResult(mWhichTeam, RESULT_OK, intent);
        Log.d("debe", "---onTeamSelected--- team - " + mWhichTeam +
                "teamId - " + idTeam);

        FragmentTransaction ft = fm.beginTransaction();
        Fragment listFragment = fm.findFragmentById(R.id.layoutForList);
        ft.remove(listFragment);
        mLayout.removeView(layoutForList);
        ft.commit();
    }

    @Override
    public void onTeamSelected(int idTeam, int whichTeam) {}
}
