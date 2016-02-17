package com.flowfree.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Malek on 2/15/2016.
 */
public class LevelsBDbHelper extends SQLiteOpenHelper {
    private static final String TABLE_LEVELS = "table_levels";
    private static final String COL_ID = "ID";
    private static final String COL_STATUS = "status";
    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_LEVELS + " ("
            + COL_ID + " INTEGER PRIMARY KEY, " + COL_STATUS + " TEXT NOT NULL);";

    public LevelsBDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BDD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_LEVELS + ";");
        onCreate(db);
    }
}
