package com.simonyan51.a51movies.db.query;

import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by simonyan51 on 07.08.2017.
 */

public abstract class DataQueryHandler {

    // ===========================================================
    // Constants
    // ===========================================================

    private static final String LOG_TAG = DataQueryHandler.class.getSimpleName();

    // ===========================================================
    // Fields
    // ===========================================================

    protected SQLiteOpenHelper mDataHelper;
    protected DataQueryListener mListener;

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass
    // ===========================================================

    // ===========================================================
    // Listeners, methods for/from Interfaces
    // ===========================================================

    // ===========================================================
    // Methods
    // ===========================================================

    public abstract void query(int token, Object o);
    public abstract void insert(int token, Object o);
    public abstract void update(int token, Object o);
    public abstract void delete(int token, Object o);

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

    public static interface DataQueryListener {

        void onQueryComplete(int token, Object o);
        void onInsertComplete(int token, Object o);
        void onUpdateComplete(int token, Object o);
        void onDeleteComplete(int token, Object o);

    }

}
