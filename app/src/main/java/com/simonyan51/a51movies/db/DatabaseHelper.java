package com.simonyan51.a51movies.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by simonyan51 on 07.08.2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = DatabaseHelper.class.getSimpleName();


    private static final String DB_NAME = "moviesDB";
    private static final int VERSION = 1;
    private String tableName;
    private String query;


    public DatabaseHelper(Context context, String tableName, String query) {
        super(context, DB_NAME, null, VERSION);
        this.tableName = tableName;
        this.query = query;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        onCreate(db);
    }


}
