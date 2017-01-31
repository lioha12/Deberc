package com.liohakonykgmail.deberc;

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
    protected int getLayoutResId()
    {
        return R.layout.layout_game_fragment;
    }
}
