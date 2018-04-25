package timerapp.jaked.timerapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TimerBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "timerBase.db";

    public TimerBaseHelper(Context context){
        super(context, DATABASE_NAME,null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("Create table " + TimerDbSchema.TimerTable.NAME
        + "(" + "_id integer primary key autoincrement,"
        + TimerDbSchema.TimerTable.Cols.UUID + ", "
        + TimerDbSchema.TimerTable.Cols.TITLE + ", "
        + TimerDbSchema.TimerTable.Cols.MINUTES + ", "
        + TimerDbSchema.TimerTable.Cols.SECONDS + ")");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
