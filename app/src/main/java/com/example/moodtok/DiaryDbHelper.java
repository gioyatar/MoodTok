package com.example.moodtok;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DiaryDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "diary.db";
    public static final String TABLE_NAME = "diary_entries";
    public static final String COL_ID = "ID";
    public static final String COL_ACTIVITIES = "activities";
    public static final String COL_DIARY_ENTRY = "diary_entry";
    public static final String COL_MOOD = "mood";

    public DiaryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COL_ACTIVITIES + " TEXT, " +
                        COL_DIARY_ENTRY + " TEXT, " +
                        COL_MOOD + " TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String activities, String diaryEntry, String mood) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ACTIVITIES, activities);
        contentValues.put(COL_DIARY_ENTRY, diaryEntry);
        contentValues.put(COL_MOOD, mood);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }
}