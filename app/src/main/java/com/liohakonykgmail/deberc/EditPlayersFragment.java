package com.liohakonykgmail.deberc;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.system.ErrnoException;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static android.app.Activity.RESULT_OK;


/**
 * Created by lioha on 18.01.17.
 */

public class EditPlayersFragment extends Fragment {

    //public Context appContext = getContext();

    public static final String LOG_TAG_DEBERC = "deberc";

    public static final String EDIT_PLAYERS_TAG = "edit_players_fragment";

    public static final String EXTRA_PLAYER1 = "player1";
    public static final String EXTRA_PLAYER2 = "player2";
    public static final String EXTRA_PLAYER3 = "player3";
    public static final String EXTRA_PLAYER4 = "player4";
    private static final String EXTRA_TEAM1_NAME = "team1";
    private static final String EXTRA_TEAM2_NAME = "team2";

    private TextView mTeam1TextView;
    private TextView mTeam2TextView;
    private Button mBtnNewGamer;
    private Button mResetButton;
    private Button mBtnStart;
    private Button mBtnTeam1Choice;
    private Button mBtnTeam2Choice;
    private Spinner mSpinnerForTeam1;
    private Spinner mSpinnerForTeam2;
    private TextView mTextViewNamesTeam1;
    private TextView mTextViewNamesTeam2;
    private RadioButton mRadioButton1000;
    private RadioButton mRadioButton500;
    private GamerDeberc mGamerDeberc1;
    private GamerDeberc mGamerDeberc2;
    private GamerDeberc mGamerDeberc3;
    private GamerDeberc mGamerDeberc4;
    private TeamOfGamer mTeam1;
    private TeamOfGamer mTeam2;
    private Callbacks mCallbacks;
    private GlobalGame mGlobalGame;

    private ArrayAdapter<String> mAdapter;
    public static EditPlayersFragment fragment;

    public interface Callbacks{
        void onGameStarted(GlobalGame globalGame);
        void onTeam1ChoiceButtonPressed();
        void onTeam2ChoiceButtonPressed();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks)context;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    GamerDeberc.loadGamers(new GamerDeberc.GamersDBHelper(getActivity()));
                    TeamOfGamer.loadTeams(new TeamOfGamer.TeamsDBHelper(getActivity()));
                }catch (Exception e){
                    Log.d("debe", "onAttach EdPF");
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDetach(){
        super.onDetach();
        mCallbacks = null;
        fragment = null;
        Log.d("debe", "onDetach EditPlFragment");
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        fragment = this;
        setHasOptionsMenu(true);
        if(savedInstanceState != null){
            try {
                String name1 = savedInstanceState.getString(EXTRA_PLAYER1);
                String name2 = savedInstanceState.getString(EXTRA_PLAYER2);
                String name3 = savedInstanceState.getString(EXTRA_PLAYER3);
                String name4 = savedInstanceState.getString(EXTRA_PLAYER4);

                for (GamerDeberc gd : GamerDeberc.getGamers()) {              //identification gamers

                    if (name1.equals(gd.getName())) {
                        mGamerDeberc1 = gd;
                    } else if (name2.equals(gd.getName())) {
                        mGamerDeberc2 = gd;
                        mTeam1 = new TeamOfGamer(mGamerDeberc1, mGamerDeberc2);
                    } else if (name3.equals(gd.getName())) {
                        mGamerDeberc3 = gd;
                    } else if (name4.equals(gd.getName())) {
                        mGamerDeberc4 = gd;
                        mTeam2 = new TeamOfGamer(mGamerDeberc3, mGamerDeberc4);
                    } else continue;

                }

                mTextViewNamesTeam1.setText(mTeam1.getNameOfTeam());
                mTextViewNamesTeam2.setText(mTeam2.getNameOfTeam());
            }catch (Exception e){}
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.edit_players_layout, container, false);

        mTeam1TextView = (TextView)v.findViewById(R.id.team_text_view1);
        mTeam2TextView = (TextView)v.findViewById(R.id.team_text_view2);
        mTextViewNamesTeam1 = (TextView)v.findViewById(R.id.team1_names_tv);
        mTextViewNamesTeam2 = (TextView)v.findViewById(R.id.team2_names_tv);
        mBtnNewGamer = (Button)v.findViewById(R.id.btn_new_gamer_add_in_list);
        mBtnStart = (Button)v.findViewById(R.id.btn_start);
        mSpinnerForTeam1 = (Spinner)v.findViewById(R.id.team1_spinner);
        mSpinnerForTeam2 = (Spinner)v.findViewById(R.id.team2_spinner);
        mRadioButton500 = (RadioButton)v.findViewById(R.id.rb_500);
        mRadioButton1000 = (RadioButton)v.findViewById(R.id.rb_1000);

        mBtnNewGamer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new NewGamerNameDialog().show(getFragmentManager(), NewGamerNameDialog.NEW_GAMER_DIALOG);
            }
        });
        updateSpinner();

        mSpinnerForTeam1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = parent.getItemAtPosition(position).toString();
                if(name.equals(""))return;

                for(GamerDeberc gd:GamerDeberc.getGamers()){
                    if(gd.getName().equals(name)){
                        if(mGamerDeberc1 == null){
                            mGamerDeberc1 = gd;
                        }else if(mGamerDeberc2 == null){
                            mGamerDeberc2 = gd;
                            mTeam1 = new TeamOfGamer(mGamerDeberc1, mGamerDeberc2);
                            mTextViewNamesTeam1.setText(mTeam1.getNameOfTeam());
                            /*Thread thread = new Thread(new Runnable() {
                                @Override
                                public void run() {*/
                                    TeamOfGamer.addTeam(mTeam1, new TeamOfGamer.TeamsDBHelper(getActivity()));
                              /*  }
                            });
                            thread.start();
                            try {
                                thread.join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }*/
                        }else {//mGlobalPoints.setText(game.getGameWinPoints());
                            mTextViewNamesTeam1.setText(mTeam1.getNameOfTeam());
                        }
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mTextViewNamesTeam1.setText("");
                mTeam1 = null;
            }
        });

        mSpinnerForTeam2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = parent.getItemAtPosition(position).toString();
                if(name.equals("")){
                    return;
                }
                for(GamerDeberc gd:GamerDeberc.getGamers()){
                    if(gd.getName().equals(name)){
                        if(mGamerDeberc3 == null){
                            mGamerDeberc3 = gd;
                        }else if(mGamerDeberc4 == null){
                            mGamerDeberc4 = gd;
                            mTeam2 = new TeamOfGamer(mGamerDeberc3, mGamerDeberc4);
                            mTextViewNamesTeam2.setText(mTeam2.getNameOfTeam());

                            Thread thread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    TeamOfGamer.addTeam(mTeam2,
                                            new TeamOfGamer.TeamsDBHelper(getActivity()));
                                }
                            });
                            thread.start();
                            try {
                                thread.join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }else {
                            mTextViewNamesTeam2.setText(mTeam2.getNameOfTeam());
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mTextViewNamesTeam2.setText("");
                mTeam2 = null;
            }
        });

        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = 0;
                if(mRadioButton500.isChecked()) {
                    i = 501;
                }
                else if(mRadioButton1000.isChecked()){
                    i = 1001;
                }
                else {
                    Toast.makeText(getActivity(), "Не выбран размер игры", Toast.LENGTH_LONG).show();
                    return;
                }
                if (mTeam2 == null||mTeam1 == null)
                {
                    Toast.makeText(getActivity(), "Не указаны все игроки", Toast.LENGTH_LONG).show();
                    return;
                }
                mGlobalGame = new GlobalGame(mTeam1, mTeam2, i);

                mCallbacks.onGameStarted(mGlobalGame);
            }
        });

        mResetButton = (Button)v.findViewById(R.id.resetBtn);
        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGamerDeberc1 = null;
                mGamerDeberc2 = null;
                mGamerDeberc3 = null;
                mGamerDeberc4 = null;
                mTextViewNamesTeam2.setText("");
                mTextViewNamesTeam1.setText("");
            }
        });

        mBtnTeam1Choice = (Button)v.findViewById(R.id.button_team1_choice);
        mBtnTeam2Choice = (Button)v.findViewById(R.id.button_team2_choice);

        mBtnTeam1Choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Intent intent = new Intent(getActivity(), ActivityTeamsList.class);
                startActivityForResult(intent, 1);*/
                mCallbacks.onTeam1ChoiceButtonPressed();
            }
        });
        mBtnTeam2Choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Intent intent = new Intent(getActivity(), ActivityTeamsList.class);
                startActivityForResult(intent, 2);*/
                mCallbacks.onTeam2ChoiceButtonPressed();
            }
        });
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_delete_all, menu);

        //MenuItem item = menu.findItem(R.id.menu_item_delete_gamers);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_item_delete_gamers:
                GamerDeberc.cleanDBGamers(new GamerDeberc.GamersDBHelper(getActivity()));
                updateSpinner();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void updateSpinner()
    {
        mAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, GamerDeberc.getGamersName());
        mSpinnerForTeam1.setAdapter(mAdapter);
        mSpinnerForTeam2.setAdapter(mAdapter);
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        if (mGamerDeberc1 != null){
            savedInstanceState.putString(EXTRA_PLAYER1, mGamerDeberc1.getName());
        }
        if (mGamerDeberc2 != null){
            savedInstanceState.putString(EXTRA_PLAYER2, mGamerDeberc2.getName());
        }
        if (mGamerDeberc3 != null){
            savedInstanceState.putString(EXTRA_PLAYER3, mGamerDeberc3.getName());
        }
        if (mGamerDeberc4 != null){
            savedInstanceState.putString(EXTRA_PLAYER4, mGamerDeberc4.getName());
        }

        /*if (mTeam1 != null){
            savedInstanceState.putString(EXTRA_TEAM1_NAME, mTeam1.getNameOfTeam());
        }
        if (mTeam2 != null){
            savedInstanceState.putString(EXTRA_TEAM2_NAME, mTeam2.getNameOfTeam());
        }*/
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (data == null){
            Log.d("debe", "data = null");
            return;}
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case 1:
                   int teamId = data.getIntExtra(FragmetListTeams.TEAM_EXTRA, 0);
                    for(TeamOfGamer teamOfGamer:TeamOfGamer.getTeams()){
                        if(teamOfGamer.getId() == teamId){
                            mTeam1 = teamOfGamer;
                            Log.d("debe", "EPF = " + mTeam1.getNameOfTeam());
                            mTextViewNamesTeam1.setText(mTeam1.getNameOfTeam());
                            break;
                        }
                    }
                    break;
                case 2:
                    int teamId2 = data.getIntExtra(FragmetListTeams.TEAM_EXTRA, 0);
                    for(TeamOfGamer teamOfGamer:TeamOfGamer.getTeams()){
                        if(teamOfGamer.getId() == teamId2){
                            mTeam2 = teamOfGamer;
                            mTextViewNamesTeam2.setText(mTeam2.getNameOfTeam());
                            break;
                        }
                    }
                    break;
            }
        }
    }

}
