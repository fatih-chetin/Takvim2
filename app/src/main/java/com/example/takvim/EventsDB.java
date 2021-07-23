package com.example.takvim;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class EventsDB {
    public  static final String KEY_ROWID = "_id";
    public  static final String KEY_NAME = "event_name";
    public  static final String KEY_DATE = "event_date";
    public  static final String KEY_START = "start_time";
    public  static final String KEY_END = "end_time";

    private final String DATABASE_NAME = "EventsDB";
    private final String DATABASE_TABLE = "EventsTable";
    private final int DATABASE_VERSION = 1;

    private DBHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDataBase;

    public EventsDB(Context ourContext) {
        this.ourContext = ourContext;
    }

    private class DBHelper extends SQLiteOpenHelper{

        public DBHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String sqlCode= "CREATE TABLE "+DATABASE_TABLE+" ("+
                    KEY_ROWID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    KEY_NAME+" TEXT NOT NULL, "+
                    KEY_DATE+" TEXT NOT NULL, "+
                    KEY_START+" TEXT NOT NULL, "+
                    KEY_END+" TEXT NOT NULL);";
            db.execSQL(sqlCode);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
            onCreate(db);

        }
    }
    public EventsDB open() throws SQLException
    {
        ourHelper= new DBHelper(ourContext);
        ourDataBase= ourHelper.getWritableDatabase();
        return this;
    }
    public void close(){
        ourHelper.close();
    }
    public long createEntry(String eventName,String Date, String startTime, String EndTime){
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME,eventName);
        cv.put(KEY_DATE,Date);
        cv.put(KEY_START,startTime);
        cv.put(KEY_END,EndTime);
        return ourDataBase.insert(DATABASE_TABLE,null,cv);
    }

    public String getData(){
        String [] columns= new String[] {KEY_ROWID,KEY_NAME,KEY_DATE,KEY_START,KEY_END};
        Cursor c= ourDataBase.query(DATABASE_TABLE,columns,null,null,null,null,null);

        String result="";
        int iRow=c.getColumnIndex(KEY_ROWID);
        int iName=c.getColumnIndex(KEY_NAME);
        int iDate=c.getColumnIndex(KEY_DATE);
        int iStart=c.getColumnIndex(KEY_START);
        int iEnd=c.getColumnIndex(KEY_END);

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            result=result+c.getString(iRow)+": "+c.getString(iName)+" "+
                    c.getString(iDate)+" "+
                    c.getString(iStart)+" "+
                    c.getString(iEnd)+"\n";
        }
        c.close();

        return result;
    }

    public long deleteEntry(String rowId){
        return ourDataBase.delete(DATABASE_TABLE,KEY_ROWID+"=?",new String[]{rowId});
    }

    public long updateEntry(String rowId,String name,String date,String start,String end){
        ContentValues cv=new ContentValues();
        cv.put(KEY_NAME,name);
        cv.put(KEY_DATE,date);
        cv.put(KEY_START,start);
        cv.put(KEY_END,end);

        return ourDataBase.update(DATABASE_TABLE,cv,KEY_ROWID+"=?",new String[]{rowId});

    }
}
