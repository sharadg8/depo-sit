package com.sharad.finance.deposit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Sharad on 15-Jun-14.
 */
public class DBAdapter {
    // For logging
    private static final String TAG = "DBAdapter";

    public static final int DATABASE_VERSION = 1;

    // DB Item FD table fields
    public static final String KEY_FD_ROWID      = "_id";
    public static final String KEY_FD_TITLE      = "title";
    public static final String KEY_FD_BANK       = "bank";
    public static final String KEY_FD_ACCNUM     = "acc_num";
    public static final String KEY_FD_DATE       = "date";
    public static final String KEY_FD_END_DATE   = "end_date";
    public static final String KEY_FD_TYPE       = "type";
    public static final String KEY_FD_AMOUNT     = "amount";
    public static final String KEY_FD_RATE       = "rate";
    public static final String KEY_FD_EARNED     = "earned";
    public static final String KEY_FD_TDS        = "tds";
    public static final String KEY_FD_STATUS     = "status";
    public static final String KEY_FD_TENURE     = "tenure";


    public static final int COL_FD_ROWID     = 0;
    public static final int COL_FD_TITLE     = 1;
    public static final int COL_FD_BANK      = 2;
    public static final int COL_FD_ACCNUM    = 3;
    public static final int COL_FD_DATE      = 4;
    public static final int COL_FD_END_DATE  = 5;
    public static final int COL_FD_TYPE      = 6;
    public static final int COL_FD_AMOUNT    = 7;
    public static final int COL_FD_RATE      = 8;
    public static final int COL_FD_EARNED    = 9;
    public static final int COL_FD_TDS       = 10;
    public static final int COL_FD_STATUS    = 11;
    public static final int COL_FD_TENURE    = 12;

    public static final String[] ALL_KEYS_FD = new String[] {KEY_FD_ROWID, KEY_FD_TITLE,
            KEY_FD_BANK, KEY_FD_ACCNUM, KEY_FD_DATE, KEY_FD_END_DATE, KEY_FD_TYPE, KEY_FD_AMOUNT,
            KEY_FD_RATE, KEY_FD_EARNED, KEY_FD_TDS, KEY_FD_STATUS, KEY_FD_TENURE   };

    public static final String DATABASE_NAME = "savings";
    public static final String DATABASE_TABLE_FD = "fd_table";

    private static final String DATABASE_CREATE_SQL_FD = "create table " + DATABASE_TABLE_FD
            + " ("
            + KEY_FD_ROWID     + " integer primary key autoincrement, "
            + KEY_FD_TITLE     + " text not null, "
            + KEY_FD_BANK      + " text not null, "
            + KEY_FD_ACCNUM    + " text not null, "
            + KEY_FD_DATE      + " text not null, "
            + KEY_FD_END_DATE  + " text not null, "
            + KEY_FD_TYPE      + " text not null, "
            + KEY_FD_AMOUNT    + " float not null, "
            + KEY_FD_RATE      + " float not null, "
            + KEY_FD_EARNED    + " float not null, "
            + KEY_FD_TDS       + " float not null, "
            + KEY_FD_STATUS    + " integer not null, "
            + KEY_FD_TENURE    + " integer not null"
            + ");";

    private final Context context;

    private DatabaseHelper myDBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx) {
        this.context = ctx;
        myDBHelper = new DatabaseHelper(context);
    }

    // Open the database connection.
    public DBAdapter open() {
        db = myDBHelper.getWritableDatabase();

        // Enable foreign key constraints
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys = ON;");
        }

        return this;
    }

    // Close the database connection.
    public void close() {
        myDBHelper.close();
    }

    /*
       ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
       +++++++++++++++++++++ FD RECORD METHODS ++++++++++++++++++++++
       ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    */
    public long insertFD(String title, String bank, String accNum, String date, String end_date, String type,
                         float amount, float rate, float earned, float tds, int status, int tenure) {
        // Create row's data:
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_FD_TITLE, title);
        initialValues.put(KEY_FD_BANK, bank);
        initialValues.put(KEY_FD_ACCNUM, accNum);
        initialValues.put(KEY_FD_DATE, date);
        initialValues.put(KEY_FD_END_DATE, end_date);
        initialValues.put(KEY_FD_TYPE, type);
        initialValues.put(KEY_FD_AMOUNT, amount);
        initialValues.put(KEY_FD_RATE, rate);
        initialValues.put(KEY_FD_EARNED, earned);
        initialValues.put(KEY_FD_TDS, tds);
        initialValues.put(KEY_FD_STATUS, status);
        initialValues.put(KEY_FD_TENURE, tenure);

        // Insert it into the database.
        return db.insert(DATABASE_TABLE_FD, null, initialValues);
    }

    public boolean deleteFD(long rowId) {
        String where = KEY_FD_ROWID + "=" + rowId;
        return db.delete(DATABASE_TABLE_FD, where, null) != 0;
    }

    public Cursor getAllFDs() {
        String where = null;
        Cursor c = 	db.query(true, DATABASE_TABLE_FD, ALL_KEYS_FD,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public Cursor getFD(long rowId) {
        String where = KEY_FD_ROWID + "=" + rowId;
        Cursor c = 	db.query(true, DATABASE_TABLE_FD, ALL_KEYS_FD,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public boolean updateFD(long rowId, String title, String bank, String accNum, String date, String end_date, String type,
                            float amount, float rate, float earned, float tds, int status, int tenure) {
        String where = KEY_FD_ROWID + "=" + rowId;

        // Create row's data:
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_FD_TITLE, title);
        newValues.put(KEY_FD_BANK, bank);
        newValues.put(KEY_FD_ACCNUM, accNum);
        newValues.put(KEY_FD_DATE, date);
        newValues.put(KEY_FD_END_DATE, end_date);
        newValues.put(KEY_FD_TYPE, type);
        newValues.put(KEY_FD_AMOUNT, amount);
        newValues.put(KEY_FD_RATE, rate);
        newValues.put(KEY_FD_EARNED, earned);
        newValues.put(KEY_FD_TDS, tds);
        newValues.put(KEY_FD_STATUS, status);
        newValues.put(KEY_FD_TENURE, tenure);

        // Insert it into the database.
        return db.update(DATABASE_TABLE_FD, newValues, where, null) != 0;
    }

    public void deleteAllFDs() {
        Cursor c = getAllFDs();
        long rowId = c.getColumnIndexOrThrow(KEY_FD_ROWID);
        if (c.moveToFirst()) {
            do {
                deleteFD(c.getLong((int) rowId));
            } while (c.moveToNext());
        }
        c.close();
    }

    /////////////////////////////////////////////////////////////////////

     public void cleanDeposits() {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_FD);
        db.execSQL(DATABASE_CREATE_SQL_FD);
    }

    /////////////////////////////////////////////////////////////////////
    //	Private Helper Classes:
    /////////////////////////////////////////////////////////////////////

    /**
     * Private class which handles database creation and upgrading.
     * Used to handle low-level database access.
     */
    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase _db) {
            _db.execSQL(DATABASE_CREATE_SQL_FD);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading application's database from version " + oldVersion
                    + " to " + newVersion + ", which will destroy all old data!");

            // Destroy old database:
            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_FD);

           // Recreate new database:
            onCreate(_db);
        }
    }
}
