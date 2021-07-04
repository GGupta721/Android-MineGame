package com.example.minegame;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialogFragment;

/**
 * This class is a alert box fragment that is appears after the user has won the game. When the player clicks ok, the main menu appears
 * where the player can choose the options again.
 */
public class WinMessageFragment extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.win_message, null);
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        getActivity().finish();
                        break;
                }
            }
        };
        return new AlertDialog.Builder(getActivity())
                .setTitle("You Won !")
                .setView(v)
                .setPositiveButton(android.R.string.ok, listener)
                .create();
    }
}
