package com.simonyan51.a51movies.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;

import com.simonyan51.a51movies.pojo.User;

/**
 * Created by simonyan51 on 07.08.2017.
 */

public class PreferenceHelper {

    // ===========================================================
    // Constants
    // ===========================================================

    private static final String PREF_USER_ID = "PREF_USER_ID";
    private static final String PREF_USER_MAIL = "PREF_USER_MAIL";
    private static final String PREF_USER_PASS = "PREF_USER_PASS";
    private static final String PREF_USER_ADMIN = "PREF_USER_ADMIN";

    // ===========================================================
    // Fields
    // ===========================================================

    private static PreferenceHelper sInstance;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    // ===========================================================
    // Constructors
    // ===========================================================

    private PreferenceHelper(Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mEditor = mSharedPreferences.edit();
    }

    public static PreferenceHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PreferenceHelper(context);
        }

        return sInstance;
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    public long getUserId() {
        return mSharedPreferences.getLong(PREF_USER_ID, 0);
    }

    public String getUserMail() {
        return mSharedPreferences.getString(PREF_USER_MAIL, null);
    }

    public String getUserPass() {
        return mSharedPreferences.getString(PREF_USER_PASS, null);
    }

    public int getUserAdmin() {
        return mSharedPreferences.getInt(PREF_USER_ADMIN, 0);
    }

    public void setUserId(long id) {
        mEditor.putLong(PREF_USER_ID, id);
        mEditor.apply();
    }

    public void setUserPass(String userName) {
        mEditor.putString(PREF_USER_PASS, userName);
        mEditor.apply();
    }

    public void setUserMail(String userMail) {
        mEditor.putString(PREF_USER_MAIL, userMail);
        mEditor.apply();
    }

    public void setUserAdmin(int isAdmin) {
        mEditor.putInt(PREF_USER_ADMIN, isAdmin);
        mEditor.apply();
    }

    public void setUser(User user) {
        setUserId((user).getId());
        setUserMail((user).getEmail());
        setUserPass((user).getPassword());
        setUserAdmin((user).getIsAdmin());
    }

    public void deleteUserData() {
        setUserId(-1);
        setUserMail(null);
        setUserPass(null);
        setUserAdmin(0);
        mEditor.apply();
    }

    // ===========================================================
    // Listeners
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    // ===========================================================
    // Methods
    // ===========================================================

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
