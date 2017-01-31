package com.liohakonykgmail.deberc;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by lioha on 17.01.17.
 */

public class DebercFragment extends Fragment {

    public static final String DEBERC_FRAGMENT_TAG = "deberc_fragment";
    private GlobalGame game;
    private DebercGame currentGame;
    private Spinner mSpinnerGameCount;
    private Spinner mSpinnerGlobalGamePoints;
    private EditText editCalculatedPoints;
    private TextView mGlobalPoints;

    @Override
    public void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);
        int globalGameWinPoints = (int)getArguments().getSerializable(GlobalGame.EXTRA_GAME_WIN_POINTS);
        TeamOfGamer teamOfGamer1 = new TeamOfGamer(new GamerDeberc((String)getArguments().
                getSerializable(EditPlayersFragment.EXTRA_PLAYER1)),
                new GamerDeberc((String)getArguments().
                        getSerializable(EditPlayersFragment.EXTRA_PLAYER2)));

        TeamOfGamer teamOfGamer2 = new TeamOfGamer(new GamerDeberc((String)getArguments().
                getSerializable(EditPlayersFragment.EXTRA_PLAYER3)),
                new GamerDeberc((String)getArguments().
                        getSerializable(EditPlayersFragment.EXTRA_PLAYER4)));

        game = new GlobalGame(teamOfGamer1, teamOfGamer2);
        game.setGameWinPoints(globalGameWinPoints);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.game_layout_fragment, container, false);


        ArrayAdapter<String> gameCountAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_item, DebercGame.gamePoints);

        ArrayAdapter<String> globalPointsAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_item, new String[]{"1001", "501"});

        mSpinnerGameCount = (Spinner)v.findViewById(R.id.spinner_game_count);
        mSpinnerGameCount.setAdapter(gameCountAdapter);
        mSpinnerGameCount.setPrompt("clksjafdns;k");
        mSpinnerGlobalGamePoints = (Spinner)v.findViewById(R.id.spinner_global_game_points);
        mSpinnerGlobalGamePoints.setAdapter(globalPointsAdapter);
        mSpinnerGlobalGamePoints.setPrompt("dsaf");
        mGlobalPoints = (TextView)v.findViewById(R.id.text_view_game_win_points);
        //mGlobalPoints.setText(game.getGameWinPoints());

        mSpinnerGameCount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //currentGame.setCurrentGame(Integer.parseInt((String)parent.getItemAtPosition(position)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSpinnerGlobalGamePoints.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //game.setGameWinPoints(Integer.parseInt((String)parent.getItemAtPosition(position)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        editCalculatedPoints = (EditText)v.findViewById(R.id.calculated_current_points);
        editCalculatedPoints.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    currentGame.setCalculatedPoints(Integer.parseInt((String) s));
                }catch (Exception e){
                    Toast.makeText(getActivity(), "И что это такое?", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return v;
    }

    public void setGame(GlobalGame game) {
        this.game = game;
    }
}