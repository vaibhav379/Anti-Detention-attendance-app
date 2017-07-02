package com.darte.vaibhav.anti_detention;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by VAIBHAV on 23-08-2016.
 */
public class reminderDatabase extends SQLiteOpenHelper {


    public static final String DATABASE_NAME1="Reminders.db";
    public static final String TABLE_NAME1="reminder";
    public static final String COL5="SUBJECT";
    public static final String COL6="HOUR";
    public static final String COL7="MINUTE";
    //public static final String COL8="DAY";




    Reminders reminders=new Reminders();

    public reminderDatabase(Context context) {
        super(context, DATABASE_NAME1, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME1 + " (SUBJECT TEXT,HOUR INTEGER,MINUTE INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        onCreate(db);
    }


    public void addtime(String subject,int hour,int minute){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL5,subject);
        contentValues.put(COL6,hour);
        contentValues.put(COL7,minute);
        db.insert(TABLE_NAME1,null,contentValues);
        reminders.setcal(hour,minute);


    }
}
