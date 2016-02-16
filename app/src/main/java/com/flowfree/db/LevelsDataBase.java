package com.flowfree.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Malek on 2/15/2016.
 */
public class LevelsDataBase {
    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "levels.db";

    private static final String TABLE_LEVELS = "table_levels";
    private static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;
    private static final String COL_STATUS = "status";
    private static final int NUM_COL_STATUS = 1;
    private SQLiteDatabase bdd;
    private LevelsBDbHelper myBaseSQLite;

    public LevelsDataBase(Context context) {
        myBaseSQLite = new LevelsBDbHelper(context, NOM_BDD, null, VERSION_BDD);
    }

    public void open() {

        bdd = myBaseSQLite.getWritableDatabase();
    }

    public void close() {
        bdd.close();
    }

    public SQLiteDatabase getBDD() {
        return bdd;
    }
    public long insertLevel(Level level){
        ContentValues values = new ContentValues();
        values.put(COL_ID,level.getId());
        values.put(COL_STATUS,level.getStatus());
        return bdd.insert(TABLE_LEVELS,null, values);
    }
    public int updateLevel(int id, Level level){
        ContentValues values = new ContentValues();
        values.put(COL_ID,level.getId());
        values.put(COL_STATUS,level.getStatus());
        return bdd.update(TABLE_LEVELS, values, COL_ID + " = " + id, null);
    }
    public int getLevelStatus(int id){
      // Cursor c = bdd.query(TABLE_LEVELS, new String[]{COL_ID, COL_STATUS}, COL_ID + "=" + id + "", null, null, null, null);
       Cursor c = bdd.rawQuery("SELECT status FROM " + TABLE_LEVELS + "  WHERE ID = " + id, null);
        c.moveToFirst();
       System.out.println(c.getInt(0));

        return c.getInt(0);

    }
    public boolean isEmpty(){
        int numRows = (int) DatabaseUtils.longForQuery(bdd, "SELECT COUNT(*) FROM table_levels", null);
        if (numRows == 0)
            return true;
        else
        return false;
    }
    private Level cursorToLevel(Cursor c){

        if (c.getCount() == 0)
            return null;
        c.moveToFirst();
        Level level = new Level();
        level.setId(c.getInt(NUM_COL_ID));
        level.setStatus(c.getInt(NUM_COL_STATUS));
        c.close();
        return level;
    }

}
