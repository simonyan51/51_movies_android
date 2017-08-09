package com.simonyan51.a51movies.db.cursor;

import android.database.Cursor;
import android.support.annotation.Nullable;

import com.simonyan51.a51movies.db.table.UserDatabase;
import com.simonyan51.a51movies.pojo.User;

import java.util.ArrayList;

/**
 * Created by simonyan51 on 07.08.2017.
 */

public class CursorReader {

    // ===========================================================
    // Constants
    // ===========================================================

    private final static String LOG_TAG = CursorReader.class.getSimpleName();

    // ===========================================================
    // Fields
    // ===========================================================

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

    /**
     * PRODUCT
     *************************************************************/

    @Nullable
    public static User parseUser(Cursor cursor) {
        User user = null;
        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {
            user = composeUser(cursor);
        }

        if (cursor != null) {
            cursor.close();
        }
        return user;
    }

    public static ArrayList<User> parseUsers(Cursor cursor) {
        ArrayList<User> userArrayList = new ArrayList<>();
        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {
            do {
                User user = composeUser(cursor);
                userArrayList.add(user);

            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        return userArrayList;
    }

    /**
     * UTIL METHODS
     *************************************************************/

    private static User composeUser(Cursor cursor) {
        User user = new User();
        user.setId(cursor.getLong(cursor.getColumnIndex(UserDatabase.ID)));
        user.setUsername(cursor.getString(cursor.getColumnIndex(UserDatabase.USERNAME)));
        user.setFirstName(cursor.getString(cursor.getColumnIndex(UserDatabase.FIRST_NAME)));
        user.setLastName(cursor.getString(cursor.getColumnIndex(UserDatabase.LAST_NAME)));
        user.setBirthDate(cursor.getString(cursor.getColumnIndex(UserDatabase.DATE_BIRTH)));
        user.setGender(cursor.getString(cursor.getColumnIndex(UserDatabase.GENDER)));
        user.setEmail(cursor.getString(cursor.getColumnIndex(UserDatabase.EMAIL)));
        user.setPassword(cursor.getString(cursor.getColumnIndex(UserDatabase.PASSWORD)));
        user.setIsAdmin(cursor.getInt(cursor.getColumnIndex(UserDatabase.ADMIN)));
        return user;
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

}
