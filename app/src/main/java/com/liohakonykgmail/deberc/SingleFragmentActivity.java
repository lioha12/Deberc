package com.liohakonykgmail.deberc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;



/**
 * Created by lioha on 15.11.16.
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {
    protected abstract Fragment createFragment();
    protected abstract String getTAG();
    protected abstract void sendArguments(Fragment fragment);

    @LayoutRes
    protected int getLayoutResId()
    {
        return R.layout.activity_fragment;
    }
    protected int getContainerId(){return R.id.fragment_container;}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(getContainerId());

        if(fragment == null)
        {
            fragment = createFragment();
            sendArguments(fragment);
            fm.beginTransaction()
                    .add(getContainerId(), fragment, getTAG())
                    .commit();
        }
    }
}
