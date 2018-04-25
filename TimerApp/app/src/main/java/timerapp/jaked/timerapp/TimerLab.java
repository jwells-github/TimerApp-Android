package timerapp.jaked.timerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import timerapp.jaked.timerapp.database.TimerBaseHelper;
import timerapp.jaked.timerapp.database.TimerCursorWrapper;
import timerapp.jaked.timerapp.database.TimerDbSchema;

public class TimerLab {

    private static TimerLab sTimerLab;

    private List<MyTimer> mMyTimers;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static TimerLab get(Context context){
        if (sTimerLab == null){
            sTimerLab = new TimerLab(context);
        }
        return sTimerLab;
    }

    public void addTimer (MyTimer t){
        ContentValues values = getContentValues(t);
        mDatabase.insert(TimerDbSchema.TimerTable.NAME, null, values);

      //  mMyTimers.add(t);
    }


    public void removeTimer (MyTimer t){
       // mMyTimers.remove(t);
        ContentValues values = getContentValues(t);
        mDatabase.delete(TimerDbSchema.TimerTable.NAME , TimerDbSchema.TimerTable.Cols.UUID + "= ?" , new String[] {t.getId().toString()});

    }

    private TimerCursorWrapper queryTimers(String whereClause, String[] whereArgs){
      Cursor cursor = mDatabase.query(
              TimerDbSchema.TimerTable.NAME,
              null,
              whereClause,
              whereArgs,
              null,
              null,
              null
      );
        return new TimerCursorWrapper(cursor);
    }

    private TimerLab(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new TimerBaseHelper(mContext).getWritableDatabase();
       // mMyTimers = new ArrayList<>();

    }

    public List<MyTimer> getMyTimers(){
      //  return mMyTimers;
      // return new ArrayList<>();
        List<MyTimer> myTimers = new ArrayList<>();
        TimerCursorWrapper cursor = queryTimers(null, null);

        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                myTimers.add(cursor.getMyTimer());
                cursor.moveToNext();
            }

        }
        finally {
            cursor.close();
        }
        return myTimers;
    }

    public MyTimer getMyTimer(UUID id){
/*        for (MyTimer mytimer : mMyTimers){
            if(mytimer.getId().equals(id)){
                return mytimer;
            }
        }*/
        //return null;

        TimerCursorWrapper cursor = queryTimers(
                TimerDbSchema.TimerTable.Cols.UUID + " = ?", new String[] {id.toString()}
        );
        try{
            if (cursor.getCount() == 0){
                return null;
            }

            cursor.moveToFirst();
            return cursor.getMyTimer();
        }
        finally {
            cursor.close();
        }
    }

    private static ContentValues getContentValues(MyTimer myTimer){
        ContentValues values  = new ContentValues();
        values.put(TimerDbSchema.TimerTable.Cols.UUID, myTimer.getId().toString());
        values.put(TimerDbSchema.TimerTable.Cols.TITLE, myTimer.getTimerTitle());
        values.put(TimerDbSchema.TimerTable.Cols.MINUTES, myTimer.getTimerMinutes());
        values.put(TimerDbSchema.TimerTable.Cols.SECONDS, myTimer.getTimerSeconds());
        return values;
    }
}
