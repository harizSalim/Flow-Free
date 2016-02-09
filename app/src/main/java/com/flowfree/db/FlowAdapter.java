package com.flowfree.db;

/**
 * Created by Malek on 2/9/2016.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.flowfree.game.Pack;

public class FlowAdapter {

    SQLiteDatabase db;
    FlowDBHelper dbHelper;
    Context context;

    public FlowAdapter(Context c) {
        context = c;
    }

    public FlowAdapter openToRead() {
        dbHelper = new FlowDBHelper(context);
        db = dbHelper.getReadableDatabase();
        return this;
    }

    public FlowAdapter openToWrite() {
        dbHelper = new FlowDBHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        db.close();
    }

    public long insertPack(Pack p) {
        String[] cols = FlowDBHelper.TablePacksCols;
        ContentValues contentValues = new ContentValues();
        contentValues.put(cols[1], p.getName());
        contentValues.put(cols[2], p.getDescription());
        contentValues.put(cols[3], p.getFile());
        openToWrite();
        String alreadyInserted = "SELECT _id FROM " + FlowDBHelper.TablePacks + " WHERE name=?";
        Cursor c = db.rawQuery(alreadyInserted, new String[]{p.getName()});
        long value;
        if (c.moveToFirst()) {
            db.update(FlowDBHelper.TablePacks, contentValues, cols[1] + "='" + p.getName() + "'", null);
            value = c.getInt(0);
        } else {
            value = db.insert(FlowDBHelper.TablePacks, null, contentValues);
        }
        close();
        return value;
    }

    public long insertChallenge(int pack_id, String name) {
        String[] cols = FlowDBHelper.TableChallengesCols;
        ContentValues contentValues = new ContentValues();
        contentValues.put(cols[1], pack_id);
        contentValues.put(cols[2], name);
        openToWrite();
        String alreadyInserted = "SELECT _id FROM " + FlowDBHelper.TableChallenges + " WHERE pack_id =? AND name=?";
        Cursor c = db.rawQuery(alreadyInserted, new String[]{"" + pack_id, name});
        long value = -1;
        if (!c.moveToFirst()) {
            value = db.insert(FlowDBHelper.TableChallenges, null, contentValues);
        } else {
            value = c.getInt(0);
        }
        close();
        return value;
    }

    public long insertLevel(int challenge_id, int number, boolean done) {
        String[] cols = FlowDBHelper.TableLevelsCols;
        ContentValues contentValues = new ContentValues();
        contentValues.put(cols[1], challenge_id);
        contentValues.put(cols[2], number);
        contentValues.put(cols[3], done);
        contentValues.put(cols[4], 0);
        openToWrite();
        String alreadyInserted = "SELECT _id FROM " + FlowDBHelper.TableLevels + " WHERE challenge_id=? AND number=?";
        Cursor c = db.rawQuery(alreadyInserted, new String[]{"" + challenge_id, "" + number});
        long value = -1;
        if (!c.moveToFirst()) {
            value = db.insert(FlowDBHelper.TableLevels, null, contentValues);
        }
        close();
        return value;
    }

    public Pack getPack(int packId) {
        openToWrite();
        String packQuery = "SELECT _id, name, description, file FROM " + FlowDBHelper.TablePacks +
                " WHERE _id='" + packId + "'";
        Cursor c = db.rawQuery(packQuery, new String[]{});

        Pack pack = null;
        if (c.getCount() == 1 && c.moveToFirst()) {
            pack = new Pack(c.getString(1), c.getString(2), c.getString(3));
            pack.setId(c.getInt(0));
        }
        close();

        return pack;
    }

    public long setLevelDone(String packName, String challengeName, int levelNumber, boolean done) {
        openToWrite();
        String entryQuery = "SELECT l._id FROM " + FlowDBHelper.TablePacks + " p JOIN " +
                FlowDBHelper.TableChallenges + " c ON p._id=c.pack_id JOIN " +
                FlowDBHelper.TableLevels + " l ON c._id=l.challenge_id  WHERE p.name='" + packName +
                "' AND c.name='" + challengeName + "' AND l.number=" + levelNumber + "";
        Cursor c = db.rawQuery(entryQuery, new String[]{});
        long value = -1;
        if (c.getCount() == 1 && c.moveToFirst()) {
            String[] cols = FlowDBHelper.TableLevelsCols;
            ContentValues contentValues = new ContentValues();
            int finished = done ? 1 : 0;
            contentValues.put(cols[3], finished);
            value = db.update(FlowDBHelper.TableLevels, contentValues, "_id='" + c.getLong(0) + "'", null);
        }
        close();
        return value;
    }

    public long setLevelHighscore(String packName, String challengeName, int levelNumber, int highscore) {
        openToWrite();
        String entryQuery = "SELECT l._id FROM " + FlowDBHelper.TablePacks + " p JOIN " +
                FlowDBHelper.TableChallenges + " c ON p._id=c.pack_id JOIN " +
                FlowDBHelper.TableLevels + " l ON c._id=l.challenge_id  WHERE p.name='" + packName +
                "' AND c.name='" + challengeName + "' AND l.number=" + levelNumber + "";
        Cursor c = db.rawQuery(entryQuery, new String[]{});
        long value = -1;
        if (c.getCount() == 1 && c.moveToFirst()) {
            String[] cols = FlowDBHelper.TableLevelsCols;
            ContentValues contentValues = new ContentValues();
            contentValues.put(cols[4], highscore);
            value = db.update(FlowDBHelper.TableLevels, contentValues, "_id='" + c.getLong(0) + "'", null);
        }
        close();
        return value;
    }

    public boolean getLevelDone(String packName, String challengeName, int levelNumber) {
        openToWrite();
        String doneQuery = "SELECT l._id FROM " + FlowDBHelper.TablePacks + " p JOIN " +
                FlowDBHelper.TableChallenges + " c ON p._id=c.pack_id JOIN " +
                FlowDBHelper.TableLevels + " l ON c._id=l.challenge_id  WHERE p.name='" + packName +
                "' AND c.name='" + challengeName + "' AND l.number=" + levelNumber + " AND l.done=" + 1 + "";
        Cursor c = db.rawQuery(doneQuery, new String[]{});

        boolean done = false;
        if (c.getCount() == 1) {
            done = true;
        }

        close();
        return done;
    }

    public int getLevelHighscore(String packName, String challengeName, int levelNumber) {
        openToWrite();
        String doneQuery = "SELECT l._id, l.highscore AS highscore FROM " + FlowDBHelper.TablePacks + " p JOIN " +
                FlowDBHelper.TableChallenges + " c ON p._id=c.pack_id JOIN " +
                FlowDBHelper.TableLevels + " l ON c._id=l.challenge_id  WHERE p.name='" + packName +
                "' AND c.name='" + challengeName + "' AND l.number=" + levelNumber;
        Cursor c = db.rawQuery(doneQuery, new String[]{});

        if (c.moveToFirst()) {
            close();
            return c.getInt(1);
        }

        close();
        return -1;
    }

}
