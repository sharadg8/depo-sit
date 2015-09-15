package com.sharad.finance.deposit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

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
	public static final String KEY_FD_NOTE       = "note";
    public static final String KEY_FD_DATE       = "date";
    public static final String KEY_FD_END_DATE   = "end_date";
	public static final String KEY_FD_TENURE     = "tenure";
    public static final String KEY_FD_TYPE       = "type";
    public static final String KEY_FD_STATUS     = "status";
    public static final String KEY_FD_PRINCIPLE  = "principle";
    public static final String KEY_FD_RATE       = "rate";
    public static final String KEY_FD_ACC_INT    = "acc_int";
    public static final String KEY_FD_ACT_INT    = "act_int";
    public static final String KEY_FD_TDS        = "tds";

    public static final String[] ALL_KEYS_FD = new String[] {KEY_FD_ROWID, KEY_FD_TITLE,
			KEY_FD_BANK, KEY_FD_ACCNUM, KEY_FD_NOTE, KEY_FD_DATE, KEY_FD_END_DATE,
			KEY_FD_TENURE, KEY_FD_TYPE, KEY_FD_STATUS, KEY_FD_PRINCIPLE, KEY_FD_RATE,
			KEY_FD_ACC_INT, KEY_FD_ACT_INT, KEY_FD_TDS };

    public static final String DATABASE_NAME = "savings";
    public static final String DATABASE_TABLE_FD = "fd_table";

    private static final String DATABASE_CREATE_SQL_FD = "create table " + DATABASE_TABLE_FD
            + " ("
            + KEY_FD_ROWID     + " integer primary key autoincrement, "
            + KEY_FD_TITLE     + " text not null, "
            + KEY_FD_BANK      + " text not null, "
            + KEY_FD_ACCNUM    + " text not null, "
			+ KEY_FD_NOTE      + " text not null, "
            + KEY_FD_DATE      + " integer not null, "
            + KEY_FD_END_DATE  + " integer not null, "
			+ KEY_FD_TENURE    + " integer not null, "
            + KEY_FD_TYPE      + " integer not null, "
			+ KEY_FD_STATUS    + " integer not null, "
            + KEY_FD_PRINCIPLE + " float not null, "
            + KEY_FD_RATE      + " float not null, "
            + KEY_FD_ACC_INT   + " float not null, "
			+ KEY_FD_ACT_INT   + " float not null, "
            + KEY_FD_TDS       + " float not null"
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
    public long insertDeposit(Deposit deposit) {
        // Create row's data:
        ContentValues content = new ContentValues();
        content.put(KEY_FD_TITLE    , deposit.get_title());
        content.put(KEY_FD_BANK     , deposit.get_bank());
        content.put(KEY_FD_ACCNUM   , deposit.get_accNum());
		content.put(KEY_FD_NOTE     , deposit.get_note());
        content.put(KEY_FD_DATE     , deposit.get_startDate().getTime());
        content.put(KEY_FD_END_DATE , deposit.get_endDate().getTime());
        content.put(KEY_FD_TENURE   , deposit.get_tenure());
        content.put(KEY_FD_TYPE     , deposit.get_type());
        content.put(KEY_FD_STATUS   , deposit.get_status());
        content.put(KEY_FD_PRINCIPLE, deposit.get_principle());
        content.put(KEY_FD_RATE     , deposit.get_rate());
        content.put(KEY_FD_ACC_INT  , deposit.get_accInterest());
		content.put(KEY_FD_ACT_INT  , deposit.get_actInterest());
        content.put(KEY_FD_TDS      , deposit.get_tds());

        // Insert it into the database.
        return db.insert(DATABASE_TABLE_FD, null, content);
    }
	
	public boolean updateDeposit(long rowId, Deposit deposit) {
        String where = KEY_FD_ROWID + "=" + rowId;

        // Create row's data:
        ContentValues content = new ContentValues();
        content.put(KEY_FD_TITLE    , deposit.get_title());
        content.put(KEY_FD_BANK     , deposit.get_bank());
        content.put(KEY_FD_ACCNUM   , deposit.get_accNum());
		content.put(KEY_FD_NOTE     , deposit.get_note());
        content.put(KEY_FD_DATE     , deposit.get_startDate().getTime());
        content.put(KEY_FD_END_DATE , deposit.get_endDate().getTime());
        content.put(KEY_FD_TENURE   , deposit.get_tenure());
        content.put(KEY_FD_TYPE     , deposit.get_type());
        content.put(KEY_FD_STATUS   , deposit.get_status());
        content.put(KEY_FD_PRINCIPLE, deposit.get_principle());
        content.put(KEY_FD_RATE     , deposit.get_rate());
        content.put(KEY_FD_ACC_INT  , deposit.get_accInterest());
		content.put(KEY_FD_ACT_INT  , deposit.get_actInterest());
        content.put(KEY_FD_TDS      , deposit.get_tds());

        // Update it into the database.
        return db.update(DATABASE_TABLE_FD, content, where, null) != 0;
    }

    public boolean deleteDeposit(long rowId) {
        String where = KEY_FD_ROWID + "=" + rowId;
        return db.delete(DATABASE_TABLE_FD, where, null) != 0;
    }
	
	public void deleteAllDeposits() {
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_FD);
        db.execSQL(DATABASE_CREATE_SQL_FD);
    }
	
	private Deposit parseDeposit(Cursor c) {
        long id 		= c.getLong(c.getColumnIndex(KEY_FD_ROWID));
        String title 	= c.getString(c.getColumnIndex(KEY_FD_TITLE));
        String bank		= c.getString(c.getColumnIndex(KEY_FD_BANK));
        String accNum	= c.getString(c.getColumnIndex(KEY_FD_ACCNUM));
        String note		= c.getString(c.getColumnIndex(KEY_FD_NOTE));
        Date stDate		= new Date(c.getLong(c.getColumnIndex(KEY_FD_DATE)));
        Date endDate	= new Date(c.getLong(c.getColumnIndex(KEY_FD_END_DATE)));
        int tenure		= c.getInt(c.getColumnIndex(KEY_FD_TENURE));
        int type		= c.getInt(c.getColumnIndex(KEY_FD_TYPE));
        int status		= c.getInt(c.getColumnIndex(KEY_FD_STATUS));
        float principle	= c.getFloat(c.getColumnIndex(KEY_FD_PRINCIPLE));
        float rate		= c.getFloat(c.getColumnIndex(KEY_FD_RATE));
        float accInt	= c.getFloat(c.getColumnIndex(KEY_FD_ACC_INT));
        float actInt	= c.getFloat(c.getColumnIndex(KEY_FD_ACT_INT));
        float tds		= c.getFloat(c.getColumnIndex(KEY_FD_TDS));
        Deposit deposit = new Deposit(id, title, bank, accNum, note, stDate,
                endDate, tenure, type, status, principle, rate, accInt, actInt, tds);
        return deposit;
	}
	
	public Deposit getDeposit(long rowId) {
        Deposit deposit = null;
        String where = KEY_FD_ROWID + "=" + rowId;
        Cursor c = 	db.query(true, DATABASE_TABLE_FD, ALL_KEYS_FD,
                where, null, null, null, null, null);
        if(c.moveToFirst()) {
            deposit = parseDeposit(c);
        }
        return deposit;
    }
	
	public void getDeposits(ArrayList<Deposit> deposits) {
		getDeposits(deposits, null);
	}
	
	public void getDeposits(ArrayList<Deposit> deposits, String where) {
        deposits.clear();
        Cursor c = 	db.query(true, DATABASE_TABLE_FD, ALL_KEYS_FD,
                where, null, null, null, null, null);
        if (c != null) {
            if(c.moveToFirst()) {
				do {
					deposits.add(parseDeposit(c));
				} while (c.moveToNext());
			}
        }
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
