package com.liohakonykgmail.deberc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;


/**
 * Created by lioha on 15.03.17.
 */

public class ActivityTeamsList extends AppCompatActivity
        implements FragmetListTeams.CallbackForTeamList{
    FrameLayout layout;
    /*@Override
    protected Fragment createFragment() {
        return new FragmetListTeams();
    }

    @Override
    protected String getTAG() {
        return FragmetListTeams.TEAMSLIST_TAG;
    }

    @Override
    protected void sendArguments(Fragment fragment) {
    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        int whichTeam = 0;
        layout = new FrameLayout(this);
        layout.setId(R.id.listTeamsLayout);
        setContentView(layout);
        try {
            Intent intent = getIntent();
            whichTeam = intent.getIntExtra(EditPlayersActivity.TAG_TEAMS, 0);
        }catch (NullPointerException e){}

        Bundle bundle = new Bundle();
        bundle.putInt(EditPlayersActivity.TAG_TEAMS, whichTeam);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.listTeamsLayout);

        if(fragment == null){
            fragment = new FragmetListTeams();
            fragment.setArguments(bundle);
            fm.beginTransaction()
                    .add(R.id.listTeamsLayout, fragment)
                    .commit();

        }
    }


    @Override
    public void onTeamSelected(int idTeam) {
    }

    @Override
    public void onTeamSelected(int idTeam, int whichTeam) {

        //Log.d("debe", "onTeamSelected in AcTL");
        Intent intent = new Intent(this, EditPlayersActivity.class);
        intent.putExtra(FragmetListTeams.TEAM_EXTRA, idTeam);
        setResult(RESULT_OK, intent);
        EditPlayersFragment fragment = EditPlayersFragment.fragment;
        fragment.onActivityResult(whichTeam, RESULT_OK, intent);
        finish();
            //startActivity(intent);
            //FragmentManager fm = getSupportFragmentManager();
            //Fragment fragment = getSupportFragmentManager().
                    //findFragmentByTag(EditPlayersFragment.EDIT_PLAYERS_TAG);

            //if(fragment == null){
             //   Log.d("debe", "fragment is null");
            //}
            //this.onDestroy();

            //Log.d("debe", "findFragmentByTag");
            //fragment.onActivityResult(whichTeam, RESULT_OK, intent);
        //Log.d("debe", "AFTER findFragmentByTag");
    }
}
