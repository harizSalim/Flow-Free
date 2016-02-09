package com.flowfree.db;

/**
 * Created by Malek on 2/9/2016.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class FlowDBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "FLOW_DB";
    public static final int DB_VERSION = 19;

    public static final String TablePacks = "packs";
    private static final String sqlCreateTablePacks =
            "CREATE TABLE " + TablePacks + "(" +
                    " _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " name TEXT NOT NULL UNIQUE," +
                    " description TEXT," +
                    " file TEXT" +
                    ");";
    private static final String sqlDropTablePacks =
            "DROP TABLE IF EXISTS " + TablePacks;
    public static final String[] TablePacksCols = {"_id", "name", "description", "file"};
    public static final String TableChallenges = "challenges";
    private static final String sqlCreateTableChallenges =
            "CREATE TABLE " + TableChallenges + "(" +
                    " _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " pack_id INTEGER," +
                    " name TEXT NOT NULL, " +
                    "FOREIGN KEY (pack_id) REFERENCES " + TablePacks + " (_id)" +
                    ");";
    private static final String sqlDropTableChallenges =
            "DROP TABLE IF EXISTS " + TableChallenges;
    public static final String[] TableChallengesCols = {"_id", "pack_id", "name"};
    public static final String TableLevels = "levels";
    private static final String sqlCreateTableLevels =
            "CREATE TABLE " + TableLevels + "(" +
                    " _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " challenge_id INTEGER," +
                    " number INTEGER NOT NULL," +
                    " done INTEGER NOT NULL," +
                    " highscore INTEGER," +
                    " FOREIGN KEY (challenge_id) REFERENCES " + TableChallenges + " (_id)" +
                    ");";
    private static final String sqlDropTableLevels =
            "DROP TABLE IF EXISTS " + TableLevels;
    public static final String[] TableLevelsCols = {"_id", "challenge_id", "number", "done", "highscore"};

    public FlowDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreateTablePacks);
        db.execSQL(sqlCreateTableChallenges);
        db.execSQL(sqlCreateTableLevels);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(sqlDropTableLevels);
        db.execSQL(sqlDropTableChallenges);
        db.execSQL(sqlDropTablePacks);
        onCreate(db);
    }

    public void resetAllTables() {
        this.getWritableDatabase().execSQL(sqlDropTableLevels);
        this.getWritableDatabase().execSQL(sqlDropTableChallenges);
        this.getWritableDatabase().execSQL(sqlDropTablePacks);
        onCreate(this.getWritableDatabase());
    }
}
