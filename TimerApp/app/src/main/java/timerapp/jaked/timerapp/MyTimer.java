package timerapp.jaked.timerapp;

import java.util.UUID;

public class MyTimer {

    private UUID mId;
    private String mTimerTitle;
    private int mTimerMinutes;
    private int mTimerSeconds;

    public UUID getId() {
         return mId;
    }

    public MyTimer(){
       this(UUID.randomUUID());
    }

    public MyTimer(UUID id){
        mId = id;
    }

    public String getTimerTitle() {
        return mTimerTitle;
    }

    public void setTimerTitle(int timerMinutes, int timerSeconds ) {
        String timeMin = String.valueOf(timerMinutes);
        String timeSec = String.valueOf(timerSeconds);
        if (timeMin.length() < 2){
            timeMin = "0" + timeMin;
        }
        if (timeSec.length() < 2){
            timeSec = "0" + timeSec;
        }
        mTimerTitle = timeMin + ":" + timeSec;
    }

    public int getTimerMinutes() {
        return mTimerMinutes;
    }

    public void setTimerMinutes(int timerMinutes) {
        mTimerMinutes = timerMinutes;
    }

    public int getTimerSeconds() {
        return mTimerSeconds;
    }

    public void setTimerSeconds(int timerSeconds) {
        mTimerSeconds = timerSeconds;
    }
}
