package com.simonyan51.a51movies.db.query;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.simonyan51.a51movies.db.cursor.CursorReader;
import com.simonyan51.a51movies.db.table.UserDatabase;
import com.simonyan51.a51movies.pojo.User;

import java.util.ArrayList;

/**
 * Created by simonyan51 on 07.08.2017.
 */

public class UserDataQueryHandler extends DataQueryHandler {

    // ===========================================================
    // Constants
    // ===========================================================

    private static final String LOG_TAG = UserDataQueryHandler.class.getSimpleName();

    public static final class UserToken {
        public static final int INSERT = 101;
        public static final int INSERT_ALL = 102;
        public static final int UPDATE = 201;
        public static final int UPDATE_ALL = 202;
        public static final int GET = 301;
        public static final int GET_EMAIL = 302;
        public static final int GET_ALL = 303;
        public static final int DELETE = 401;
        public static final int DELETE_ALL = 402;
        public static final int ERROR = 404;
    }

    // ===========================================================
    // Fields
    // ===========================================================

    // ===========================================================
    // Constructors
    // ===========================================================

    public UserDataQueryHandler(SQLiteOpenHelper helper, DataQueryListener mListener) {
        this.mDataHelper = helper;
        this.mListener = mListener;
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass
    // ===========================================================

    @Override
    public synchronized void query(int token, Object o) {

        new Thread(() -> {

            Object result = null;
            Cursor cursor;

            SQLiteDatabase db = mDataHelper.getWritableDatabase();

            switch (token) {

                case UserToken.GET:

                    int id = (int) o;

                    cursor = db.query(
                            UserDatabase.TABLE_NAME,
                            UserDatabase.PROJECTION,
                            UserDatabase.ID + "=?",
                            new String[]{String.valueOf(id)},
                            null,
                            null,
                            null,
                            null
                    );

                    if (cursor != null) {
                        result = CursorReader.parseUser(cursor);
                    }

                    break;

                case UserToken.GET_EMAIL:

                    String email = (String) o;

                    cursor = db.query(
                            UserDatabase.TABLE_NAME,
                            UserDatabase.PROJECTION,
                            UserDatabase.EMAIL + "=?",
                            new String[]{email},
                            null,
                            null,
                            null,
                            null
                    );

                    if (cursor != null) {
                        result = CursorReader.parseUser(cursor);
                    }

                    break;

                case UserToken.GET_ALL:

                    cursor = db.query(
                            UserDatabase.TABLE_NAME,
                            UserDatabase.PROJECTION,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null
                    );

                    if (cursor != null) {
                        result = CursorReader.parseUsers(cursor);
                    }

                    break;

            }

            db.close();
            mDataHelper.close();

            mListener.onQueryComplete(result != null? token : UserToken.ERROR, result);

        }).start();

    }

    @Override
    public synchronized void insert(int token, Object o) {

        new Thread(() -> {

            long result = 0;
            ContentValues values = new ContentValues();
            SQLiteDatabase db = mDataHelper.getWritableDatabase();

            switch (token) {
                case UserToken.INSERT:

                    User user = (User) o;

                    prepareUser(values, user);

                    result = db.insert(
                            UserDatabase.TABLE_NAME,
                            null,
                            values);
                    break;

                case UserToken.INSERT_ALL:
                    ArrayList<User> users = (ArrayList<User>) o;

                    db.beginTransaction();

                    for (User currentUser : users) {

                        prepareUser(values, currentUser);

                        result = db.insert(
                                UserDatabase.TABLE_NAME,
                                null,
                                values);
                    }

                    db.setTransactionSuccessful();
                    db.endTransaction();
                    break;
            }

            db.close();
            mDataHelper.close();


            if (result != -1 && result != 0) {
                mListener.onInsertComplete(token, null);
            } else {
                mListener.onInsertComplete(UserToken.ERROR, null);
            }
        }).start();
    }

    @Override
    public synchronized void update(int token, Object o) {

        new Thread(() -> {

            long result = 0;
            ContentValues values = new ContentValues();
            SQLiteDatabase db = mDataHelper.getWritableDatabase();

            switch (token) {

                case UserToken.UPDATE:

                    User user = (User) o;

                    prepareUser(values, user);

                    result = db.update(
                            UserDatabase.TABLE_NAME,
                            values,
                            UserDatabase.ID + "=?",
                            new String[]{String.valueOf(user.getId())}
                    );

                    break;

                case UserToken.UPDATE_ALL:

                    ArrayList<User> users = (ArrayList<User>) o;

                    db.beginTransaction();

                    for (User currentUser : users) {

                        prepareUser(values, currentUser);

                        result = db.update(
                                UserDatabase.TABLE_NAME,
                                values,
                                UserDatabase.ID + "=?",
                                new String[]{String.valueOf(currentUser.getId())}
                        );
                    }

                    db.setTransactionSuccessful();
                    db.endTransaction();

                    break;

            }
            db.close();
            mDataHelper.close();



            if (result != -1 && result != 0) {
                mListener.onUpdateComplete(token, null);
            } else {
                mListener.onUpdateComplete(UserToken.ERROR, null);
            }

        }).start();
    }

    @Override
    public synchronized void delete(int token, Object o) {

        new Thread(() -> {

            long result = 0;
            SQLiteDatabase db = mDataHelper.getWritableDatabase();

            switch (token) {

                case UserToken.DELETE:

                    User user = (User) o;

                    result = db.delete(
                            UserDatabase.TABLE_NAME,
                            UserDatabase.ID + "=?",
                            new String[]{String.valueOf(user.getId())}
                    );

                    break;

                case UserToken.DELETE_ALL:

                    ArrayList<User> users = (ArrayList<User>) o;

                    db.beginTransaction();

                    for (User currentUser : users) {

                        result = db.delete(
                                UserDatabase.TABLE_NAME,
                                UserDatabase.ID + "=?",
                                new String[]{String.valueOf(currentUser.getId())}
                        );

                    }

                    db.setTransactionSuccessful();
                    db.endTransaction();

                    break;
            }


            db.close();
            mDataHelper.close();

            if (result != -1 && result != 0) {
                mListener.onDeleteComplete(token, null);
            } else {
                mListener.onDeleteComplete(UserToken.ERROR, null);
            }

        }).start();

    }

    // ===========================================================
    // Other Listeners, methods for/from Interfaces
    // ===========================================================

    // ===========================================================
    // Click Listeners
    // ===========================================================

    // ===========================================================
    // Methods
    // ===========================================================

    private void prepareUser(ContentValues values, User user) {
        if (user.getId() == 0)
            user.setId(System.currentTimeMillis());

        values.put(UserDatabase.ID, user.getId());
        values.put(UserDatabase.USERNAME, user.getUsername());
        values.put(UserDatabase.FIRST_NAME, user.getEmail());
        values.put(UserDatabase.LAST_NAME, user.getEmail());
        values.put(UserDatabase.DATE_BIRTH, user.getEmail());
        values.put(UserDatabase.GENDER, user.getEmail());
        values.put(UserDatabase.EMAIL, user.getEmail());
        values.put(UserDatabase.PASSWORD, user.getPassword());
        values.put(UserDatabase.ADMIN, user.getIsAdmin());
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
