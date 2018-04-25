package timerapp.jaked.timerapp;

import
        android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;


public class TimerCreatorDialogFragment extends DialogFragment {

    private NumberPicker mNpMinutes;
    private NumberPicker mNpSeconds;

    private int mtimerSeconds = 0;
    private int mtimerMinutes = 0;

    public static final String EXTRA_CREATE_TIMER_MINUTES = "com.jaked.android.timerapp.createtimer.minutes";
    public static final String EXTRA_CREATE_TIMER_SECONDS = "com.jaked.android.timerapp.createtimer.seconds";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_timer_creator,null);

        mNpMinutes = (NumberPicker) v.findViewById(R.id.npMinutes);
        mNpSeconds = (NumberPicker) v.findViewById(R.id.npSeconds);

        mNpMinutes.setMaxValue(59);
        mNpMinutes.setMinValue(0);

        mNpSeconds.setMaxValue(59);
        mNpSeconds.setMinValue(0);

        mNpMinutes.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
                mtimerMinutes = newVal;
            }
        });

        mNpSeconds.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
                mtimerSeconds = newVal;
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Add a timer")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sendResult(Activity.RESULT_OK,mtimerMinutes,mtimerSeconds);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null).create();

    }

    private void sendResult(int resultCode, int timerMinutes, int timerSeconds){
        if (getTargetFragment() == null){
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_CREATE_TIMER_MINUTES, timerMinutes);
        intent.putExtra(EXTRA_CREATE_TIMER_SECONDS, timerSeconds);

        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode,intent);

    }


}
