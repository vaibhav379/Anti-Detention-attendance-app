package com.darte.vaibhav.anti_detention;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VAIBHAV on 30-07-2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="Antidetention.db";
    public static final String TABLE_NAME="attendence";
    public static final String TABLE_NAME1="reminder";
    public static final String TABLE_NAME2="marks";

    public static final String COL_1="ID";
    public static final String COL_2="SUBJECT";
    public static final String COL_3="PRESENT";
    public static final String COL_4="ABSENT";

    public static final String COL21="SUBJECT";
    public static final String COL22="HOUR";
    public static final String COL23="MINUTE";

    public static final String COL31="SUBJECT";
    public static final String COL32="OBTAINED";
    public static final String COL33="TOTAL";
    public static final String COL34="TESTNAME";








    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,SUBJECT TEXT,PRESENT INTEGER,ABSENT INTEGER)");
        db.execSQL("create table " + TABLE_NAME1 + " (SUBJECT TEXT,HOUR INTEGER,MINUTE INTEGER)");
        db.execSQL("create table " + TABLE_NAME2 + " (SUBJECT TEXT,OBTAINED INTEGER,TOTAL INTEGER,TESTNAME TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);

        onCreate(db);

    }

    public boolean insertData(String subject,String present,String absent)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(COL_2, subject);
        contentValues.put(COL_3, present);
        contentValues.put(COL_4, absent);
        long insert= db.insert(TABLE_NAME,null,contentValues);
        if(insert==-1){
            return false;
        }
        else {
            return true;
        }

    }


    public List<String> getallSubject()
    {
        List<String> subjects=new ArrayList<String>();
        String selectQuery="SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()) {
            do {
                subjects.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return subjects;



    }

    public void removeSubject(String removesub)
    {

        SQLiteDatabase db=this.getWritableDatabase();
        String removeQuery="Delete from "+TABLE_NAME+" where "+COL_2+" = "+"'"+removesub+"'";
        db.execSQL(removeQuery);

    }

    public Cursor getattendence(SQLiteDatabase db)
    {
        String[] projection={COL_2,COL_3,COL_4};
        Cursor cursor= db.query(TABLE_NAME,projection,null,null,null,null,null);
        //Log.d("cursor", String.valueOf(cursor.getCount()));
        return cursor;
    }


    public void increasepres(String sub)
    {
        String query1="UPDATE "+TABLE_NAME+" SET PRESENT = PRESENT + 1 WHERE SUBJECT= "+"'"+sub+"'";

        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL(query1);



    }

    public void decreasepres(String sub)
    {
        String query1="UPDATE "+TABLE_NAME+" SET PRESENT = PRESENT - 1 WHERE SUBJECT= "+"'"+sub+"'";

        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL(query1);


    }

    public void increaseabs(String sub)
    {
        String query1="UPDATE "+TABLE_NAME+" SET ABSENT = ABSENT + 1 WHERE SUBJECT= "+"'"+sub+"'";

        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL(query1);

    }

    public void decreaseabs(String sub)
    {
        String query1="UPDATE "+TABLE_NAME+" SET ABSENT = ABSENT - 1 WHERE SUBJECT= "+"'"+sub+"'";

        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL(query1);

    }



    public void addtime(String subject,int hour,int minute){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL21,subject);
        contentValues.put(COL22,hour);
        contentValues.put(COL23, minute);
        db.insert(TABLE_NAME1, null, contentValues);
        Log.d("reminder db", "added values");
    }


    public void addmarks(String subject,int obtained,int total,String testname)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL31,subject);
        contentValues.put(COL32,obtained);
        contentValues.put(COL33,total);
        contentValues.put(COL34,testname);
        db.insert(TABLE_NAME2, null, contentValues);
        Log.d("marks db", "added values");


    }


    public Cursor getmarks(String subname)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        String[] projection={COL32,COL33,COL34};
        Cursor cursor= db.query(TABLE_NAME2,projection,COL31+" = " +"'"+subname+"'",null,null,null,null);

        return cursor;


    }



    public List<String> getexamname(String subject)
    {
        List<String> subjects=new ArrayList<String>();
        String selectQuery="SELECT TESTNAME FROM " + TABLE_NAME2+ " WHERE SUBJECT = "+"'"+subject+"'";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()) {
            do {
                subjects.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return subjects;

    }

    public void removeexam(String subject,String exam)

    {
        SQLiteDatabase db=this.getWritableDatabase();
        String removeQuery="Delete from "+TABLE_NAME2+" where SUBJECT = "+"'"+subject+"'"+ " and TESTNAME = "+"'"+exam+"'";
        db.execSQL(removeQuery);


    }

    public void removealltest(String subject){
        SQLiteDatabase db=this.getWritableDatabase();
        String removeQuery="Delete from "+TABLE_NAME2+" where SUBJECT = "+"'"+subject+"'";
        db.execSQL(removeQuery);

    }


}
