package com.example.minegame;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

/**
 * This class is a alert box fragment that is appears after the user wants to know how to play the game from the help menu
 */
public class GameInfoFragment extends AppCompatDialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.game_info_message_layout, null);

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // nothing to do finish reading
            }
        };

        return new AlertDialog.Builder(getActivity())
                .setTitle("How to play")
                .setView(v)
                .setPositiveButton(android.R.string.ok, listener).create();
    }


}
