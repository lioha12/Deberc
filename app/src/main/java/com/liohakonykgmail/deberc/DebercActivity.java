package com.liohakonykgmail.deberc;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DebercActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new DebercFragment();
    }

    @Override
    protected String getTAG() {
        return DebercFragment.DEBERC_FRAGMENT_TAG;
    }

    @Override
    protected void sendArguments(Fragment fragment) {
        Intent intent = getIntent();
        int gameWinPoints = intent.getIntExtra(GlobalGame.EXTRA_GAME_WIN_POINTS, 0);
        String name1 = intent.getStringExtra(EditPlayersFragment.EXTRA_PLAYER1);
        String name2 = intent.getStringExtra(EditPlayersFragment.EXTRA_PLAYER2);
        String name3 = intent.getStringExtra(EditPlayersFragment.EXTRA_PLAYER3);
        String name4 = intent.getStringExtra(EditPlayersFragment.EXTRA_PLAYER4);

        Bundle bundle = new Bundle();
        bundle.putInt(GlobalGame.EXTRA_GAME_WIN_POINTS, gameWinPoints);
        bundle.putString(EditPlayersFragment.EXTRA_PLAYER1, name1);
        bundle.putString(EditPlayersFragment.EXTRA_PLAYER2, name2);
        bundle.putString(EditPlayersFragment.EXTRA_PLAYER3, name3);
        bundle.putString(EditPlayersFragment.EXTRA_PLAYER4, name4);
        fragment.setArguments(bundle);
    }

    /*@Override
    protected int getLayoutResId()
    {
        return R.layout.layout_game_fragment;
    }*/
}
