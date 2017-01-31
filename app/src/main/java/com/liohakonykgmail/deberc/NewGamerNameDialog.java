package com.liohakonykgmail.deberc;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by lioha on 20.01.17.
 */

public class NewGamerNameDialog extends DialogFragment {

    private EditText mEditTextNewGamersName;
    public static final String NEW_GAMER_DIALOG = "new_gamer";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_layout, null);
        mEditTextNewGamersName = (EditText)v.findViewById(R.id.new_gamer_edit_text);
        builder.setView(v)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GamerDeberc gamerDeberc = new GamerDeberc(mEditTextNewGamersName.getText().toString());
                        GamerDeberc.getGamers().add(gamerDeberc);
                        EditPlayersFragment fragment = (EditPlayersFragment)getFragmentManager().findFragmentByTag(EditPlayersFragment.EDIT_PLAYERS_TAG);
                        fragment.updateView();
                    }
                });
        return builder.create();
    }
}
