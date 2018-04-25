package timerapp.jaked.timerapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class TimerClockFragment extends Fragment {
    private MyTimer mMyTimer;

    private TextView mTimerClock;
    private Button mButtonStartStop;
    private Button mButtonReset;
    private long mClockTimer;
    private CountDownTimer mCountDownTimer;
    private Vibrator vibrator;
    private Boolean mTimerActive = false;
    private float log1;
    int savedVolume = 100;
    private MediaPlayer mMediaPlayer;

    private boolean misVisible = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMyTimer = new MyTimer();
        mMediaPlayer = MediaPlayer.create(getActivity(),R.raw.napalm_death);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_timer_clock, container, false);

        vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);



        mTimerClock = (TextView) v.findViewById(R.id.tvTimerClock);
        mButtonStartStop = (Button) v.findViewById(R.id.btStartStop);
        mButtonStartStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSettings();
                log1=(float)(Math.log(100-savedVolume)/Math.log(100));
                mMediaPlayer.setVolume(1-log1,1-log1);
                if(!mTimerActive){
                    mTimerActive = true;
                    setTimerClock(mClockTimer);
                    mCountDownTimer.start();

                }
                else{
                    mTimerActive = false;
                    mCountDownTimer.cancel();
                }
            }
        });
        mButtonReset = (Button) v.findViewById(R.id.btReset);
        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTimerActive = false;
                mCountDownTimer.cancel();
                mClockTimer = (mMyTimer.getTimerMinutes() * 60000) + (mMyTimer.getTimerSeconds() * 1000);
                mTimerClock.setText(mMyTimer.getTimerTitle());
            }
        });
        getSettings();
        return v;
    }

    public void updateClockView(UUID uuid){
        if(mCountDownTimer!= null){mCountDownTimer.cancel();}
        mTimerActive = false;
        TimerLab timerLab = TimerLab.get(getActivity());
        mMyTimer = timerLab.getMyTimer(uuid);
        mTimerClock.setText(mMyTimer.getTimerTitle());
        mClockTimer = (mMyTimer.getTimerMinutes() * 60000) + (mMyTimer.getTimerSeconds() * 1000);



    }

    private void setTimerClock(final long clockTimer){
        mCountDownTimer = new CountDownTimer(clockTimer, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                long mMinutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                long mSeconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(mMinutes);
                System.out.println(mMinutes);
                System.out.println(mSeconds);
                String mClockText = "";
                if (mMinutes < 10){
                    mClockText += "0" + mMinutes + ":";
                }
                else{
                    mClockText += mMinutes + ":";
                }
                if (mSeconds < 10){
                    mClockText += "0" + mSeconds;
                }
                else{
                    mClockText += mSeconds;
                }
                mTimerClock.setText(mClockText);
                mClockTimer = millisUntilFinished;
            }
            @Override
            public void onFinish() {


                mTimerClock.setText("00:00");
                vibrator.vibrate(400);
              //  final MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.napalm_death);
                mTimerActive = false;
                //mp.setVolume(1-log1,1-log1);
                //mp.start();

                mMediaPlayer.start();
                if (!misVisible){
                    sendNotification();
                }

            }
        };
    }

    @Override
    public void onPause() {
        super.onPause();
        misVisible = false;

    }

    @Override
    public void onResume() {
        super.onResume();
        misVisible = true;
    }

    public void getSettings(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userSettings",Context.MODE_PRIVATE);
        savedVolume = sharedPreferences.getInt("chosen_volume",savedVolume);
    }

    public void sendNotification() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getActivity())
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle("Timer Finished")
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_HIGH)
                .setVibrate(new long[0]);

        Intent resultIntent = new Intent(getActivity(), TimerActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getActivity());
        stackBuilder.addParentStack(TimerActivity.class);

        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);


        NotificationManager mNotificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1,mBuilder.build());


    }
}

/*

                if(!timerActive){
                        timerActive = true;
                        CountDownTimer mCount =  new CountDownTimer(300000000, 1000) {

public void onTick(long millisUntilFinished) {
        long mMinutes = TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished);
        long mSeconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(mMinutes);
        mTimerClock.setText(mMinutes + ":" + mSeconds);
        }

public void onFinish() {
        timerActive = false;
        }
        }.start();*/
