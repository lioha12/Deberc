package com.liohakonykgmail.deberc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableRow;
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
    private EditText editCalculatedPoints;
    private TextView mGlobalPoints;
    private TextView mOtherPointsTextView;
    private TextView mTeam1NamesDisplay;
    private TextView mTeam2NamesDisplay;
    private TextView mTVtotalTeam1Points;
    private TextView mTVtotalTeam2Points;
    private Button mWriteButton;
    private LinearLayout mLayout;

    @Override
    public void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        int globalGameWinPoints = getArguments().getInt(GlobalGame.EXTRA_GAME_WIN_POINTS);

        TeamOfGamer teamOfGamer1 = new TeamOfGamer(new GamerDeberc(getArguments().
                getString(EditPlayersFragment.EXTRA_PLAYER1)),
                new GamerDeberc(getArguments().
                        getString(EditPlayersFragment.EXTRA_PLAYER2)));

        TeamOfGamer teamOfGamer2 = new TeamOfGamer(new GamerDeberc(getArguments().
                getString(EditPlayersFragment.EXTRA_PLAYER3)),
                new GamerDeberc(getArguments().
                        getString(EditPlayersFragment.EXTRA_PLAYER4)));

        game = new GlobalGame(teamOfGamer1, teamOfGamer2, globalGameWinPoints);

        currentGame = new DebercGame(0, 0);

        //return super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.game_layout_fragment, container, false);

        ArrayAdapter<String> gameCountAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_item, DebercGame.gamePoints);

        mOtherPointsTextView = (TextView)v.findViewById(R.id.other_points_tv);
        mSpinnerGameCount = (Spinner)v.findViewById(R.id.spinner_game_count);
        mSpinnerGameCount.setAdapter(gameCountAdapter);

        mSpinnerGameCount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(((String)parent.getItemAtPosition(position)).equals("")){
                    return;
                }else {
                    currentGame.setCurrentGame(Integer.parseInt((String) parent.getItemAtPosition(position)));
                }
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
                    currentGame.setCalculatedPoints(Integer.parseInt(s.toString()));
                    mOtherPointsTextView.setText(String.valueOf(currentGame.gameResult(currentGame)));

                }catch (Exception e){
                    //Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mGlobalPoints = (TextView)v.findViewById(R.id.text_view_game_win_points);
        mGlobalPoints.setText(String.valueOf(globalGameWinPoints));

        mTeam1NamesDisplay = (TextView)v.findViewById(R.id.tv_for_team1_name_display);      //tv's for teams names display
        mTeam2NamesDisplay = (TextView)v.findViewById(R.id.tv_for_team2_name_display);
        mTeam1NamesDisplay.setText(teamOfGamer1.getNameOfTeam());
        mTeam2NamesDisplay.setText(teamOfGamer2.getNameOfTeam());

        mLayout = (LinearLayout)v.findViewById(R.id.layout_points_view);



        mWriteButton = (Button)v.findViewById(R.id.write_button);                           //button fo write result of current game
        mWriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String team1Points = editCalculatedPoints.getText().toString();
                String team2Points = mOtherPointsTextView.getText().toString();

                game.setTeam1Points(Integer.parseInt(team1Points));
                game.setTeam2Points(Integer.parseInt(team2Points));

                mTVtotalTeam1Points.setText(String.valueOf(game.getTeam1Points()));
                mTVtotalTeam2Points.setText(String.valueOf(game.getTeam2Points()));

                LinearLayout layout = new LinearLayout(getActivity());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                layout.setLayoutParams(params);
                layout.setOrientation(LinearLayout.HORIZONTAL);
                layout.setGravity(Gravity.CENTER);

                TextView tvTeam1 = new TextView(getActivity());
                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
                //params1.setMargins(0, 0, 256, 0);
                tvTeam1.setLayoutParams(params1);
                tvTeam1.setTextSize(32);
                tvTeam1.setGravity(Gravity.CENTER);
                tvTeam1.setText(team1Points);

                TextView tvTeam2 = new TextView(getActivity());
                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
                //params2.setMargins(256, 0, 0, 0);
                tvTeam2.setLayoutParams(params2);
                tvTeam2.setTextSize(32);
                tvTeam2.setGravity(Gravity.CENTER);
                tvTeam2.setText(team2Points);

                layout.addView(tvTeam1);
                layout.addView(tvTeam2);

                mLayout.addView(layout);

                currentGame = new DebercGame(0, 0);

                mSpinnerGameCount.setSelection(0);     //reset spinner, textView and editText
                editCalculatedPoints.setText("");
                mOtherPointsTextView.setText("");
            }
        });

        mTVtotalTeam1Points = (TextView)v.findViewById(R.id.total_points_team1_tv);
        mTVtotalTeam2Points = (TextView)v.findViewById(R.id.total_points_team2_tv);

        return v;
    }
}