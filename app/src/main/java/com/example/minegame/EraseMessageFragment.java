package com.example.minegame;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
import android.text.method.DialerKeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;


/** This class is a alert box fragment that is appears after the user wants to erase all the data in the options screen
 */

public class EraseMessageFragment extends AppCompatDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        // create  the view
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.erase_message_layout,null);

        // create button listener
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){

                    case DialogInterface.BUTTON_POSITIVE:
                        TextView tv = getActivity().findViewById(R.id.playCount);
                        tv.setText("Times played: "+0);
                        SharedPreferences prefs = getActivity().getSharedPreferences(GameScreen.TIMES_PLAYED, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.clear();
                        editor.apply();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;

                }

                Log.i("TAG","You clicked the dialog");
            }
        };

        //build the alert

        return new AlertDialog.Builder(getActivity())
                .setTitle("Reset times of play to 0")
                .setView(v)
                .setPositiveButton(android.R.string.ok,listener)
                .setNegativeButton(android.R.string.cancel,listener)
                .create();
    }
}
