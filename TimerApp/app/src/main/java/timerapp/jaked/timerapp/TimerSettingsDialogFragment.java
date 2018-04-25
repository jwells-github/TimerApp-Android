package timerapp.jaked.timerapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class TimerSettingsDialogFragment extends DialogFragment {

    private SeekBar mSbVolume;
    private TextView mTvChosenVolume;
    private int savedVolume = 100;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_settings,null);
        getSettings();
        mSbVolume = (SeekBar) v.findViewById(R.id.sbVolume);
        mTvChosenVolume = (TextView) v.findViewById(R.id.tvVolume);
        mTvChosenVolume.setText(String.valueOf(savedVolume));
        mSbVolume.setProgress(savedVolume);
        mSbVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mTvChosenVolume.setText(String.valueOf(i));
                savedVolume = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Adjust the Volume")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        saveSettings();
                    }
                })
                .setNegativeButton(android.R.string.cancel,null)
                .create();

    }

    public void saveSettings(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userSettings",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("chosen_volume", savedVolume);

        editor.commit();
    }

    public void getSettings(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userSettings",Context.MODE_PRIVATE);
        savedVolume = sharedPreferences.getInt("chosen_volume",savedVolume);
    }
}
