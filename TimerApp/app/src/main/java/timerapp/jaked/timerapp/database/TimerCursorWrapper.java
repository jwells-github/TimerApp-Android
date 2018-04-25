package timerapp.jaked.timerapp.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;

import timerapp.jaked.timerapp.MyTimer;

public class TimerCursorWrapper extends CursorWrapper {
    public TimerCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public MyTimer getMyTimer(){
        String uuidString = getString(getColumnIndex(TimerDbSchema.TimerTable.Cols.UUID));
        int minutes = getInt(getColumnIndex(TimerDbSchema.TimerTable.Cols.MINUTES));
        int seconds = getInt(getColumnIndex(TimerDbSchema.TimerTable.Cols.SECONDS));

        MyTimer mytimer = new MyTimer(UUID.fromString(uuidString));
        mytimer.setTimerTitle(minutes, seconds);
        mytimer.setTimerSeconds(seconds);
        mytimer.setTimerMinutes(minutes);
        return mytimer;
    }

}
