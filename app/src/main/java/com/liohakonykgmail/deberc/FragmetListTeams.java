package com.liohakonykgmail.deberc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lioha on 15.03.17.
 */

public class FragmetListTeams extends Fragment {
    //public static final String TEAMSLIST_TAG = "teamsList";

    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private TeamListAdapter mAdapter;
    public static String TEAM_EXTRA = "team";
    public static CallbackForTeamList callbackForTeamList;
    private static int whichTeam;

    public interface CallbackForTeamList{
        void onTeamSelected(int idTeam);
        void onTeamSelected(int idTeam, int whichTeam);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callbackForTeamList = (CallbackForTeamList) getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        try {
            whichTeam = getArguments().getInt(EditPlayersActivity.TAG_TEAMS);
        }catch (NullPointerException e){
            Log.d("debe", "NPE in onCreate");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View v = inflater.inflate(R.layout.recycler_view_for_teams_layout, container, false);
        mRecyclerView = (RecyclerView)v.findViewById(R.id.recyclerViewForTeams);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new TeamListAdapter(getActivity(), TeamOfGamer.getTeams());
        Log.d("debe", "teams count in FragmentTeamList = " + TeamOfGamer.getTeams().size());
        mRecyclerView.setAdapter(mAdapter);

       return v;
    }


    public static class TeamListAdapter extends RecyclerView.Adapter {

        private ArrayList<TeamOfGamer> listTeams;
        private Context context;
        private int lastPosition = -1;

        public class TeamViewHolder extends RecyclerView.ViewHolder{
            TextView teamName;

            public TeamViewHolder(View itemView) {
                super(itemView);
                this.teamName = (TextView) itemView.findViewById(R.id.tv_card_team);

            }
        }

        public TeamListAdapter(Context context, ArrayList<TeamOfGamer> listTeams){
            this.listTeams = listTeams;
            this.context = context;
        }

        @Override
        public TeamViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_for_team, parent, false);

            return new TeamViewHolder(v);

        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            TeamViewHolder myViewHolder = (TeamViewHolder)holder;

            final TextView textView = myViewHolder.teamName;
            textView.setText(listTeams.get(position).getNameOfTeam());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (whichTeam == 0) {
                            callbackForTeamList.onTeamSelected(TeamOfGamer.getTeams().get(position).getId());
                        } else {
                            Log.d("debe", "else in holder onClick - " + whichTeam);
                            callbackForTeamList.onTeamSelected(TeamOfGamer.getTeams().get(position).getId(),
                                    whichTeam);
                        }
                    }catch (NullPointerException e){
                        Log.d("debe", "NPE in FrLT in holder");
                    }

                }
            });

        }

        @Override
        public int getItemCount() {
            return listTeams.size();
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.menu_delete_teams, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_item_delete_teams:
                TeamOfGamer.cleanTeams(new TeamOfGamer.TeamsDBHelper(getActivity()));
                mAdapter = new TeamListAdapter(getActivity(), TeamOfGamer.getTeams());
                mRecyclerView.setAdapter(mAdapter);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}