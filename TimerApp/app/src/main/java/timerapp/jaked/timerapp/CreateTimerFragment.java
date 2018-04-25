package timerapp.jaked.timerapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

public class CreateTimerFragment extends Fragment {
    private NumberPicker mNpMinutes;
    private NumberPicker mNpSeconds;
    private Button mConfirm;
    private Button mCancel;

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_timer_creator,container,false);
    }
}

//