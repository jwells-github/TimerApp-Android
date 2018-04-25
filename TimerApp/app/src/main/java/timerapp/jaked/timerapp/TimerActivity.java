package timerapp.jaked.timerapp;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.UUID;

public  class TimerActivity extends AppCompatActivity implements TimerListFragment.OnTimerSelectedListener   {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.tbMain);
        setSupportActionBar(myToolbar);


        FragmentManager fm = getSupportFragmentManager();

        Fragment fragmentTop = fm.findFragmentById(R.id.fragment_container_top);

        if (fragmentTop == null){
            fragmentTop = new TimerClockFragment();
            fm.beginTransaction().add(R.id.fragment_container_top, fragmentTop).commit();
        }

        Fragment fragmentBottom = fm.findFragmentById(R.id.fragment_container_bottom);
        if (fragmentBottom == null){
            fragmentBottom = new TimerListFragment();
            fm.beginTransaction().add(R.id.fragment_container_bottom, fragmentBottom).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_timer, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.settings:
                DialogFragment dialogFragment = new TimerSettingsDialogFragment();
                dialogFragment.show(getSupportFragmentManager(),"settings");


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void onTimerSelected(UUID uuid){
        TimerClockFragment clockFragment = (TimerClockFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container_top);

        clockFragment.updateClockView(uuid);
    }
}
