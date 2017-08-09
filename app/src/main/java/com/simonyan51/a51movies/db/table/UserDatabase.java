package com.simonyan51.a51movies.db.table;

/**
 * Created by simonyan51 on 07.08.2017.
 */

public class UserDatabase {

    public static final String TABLE_NAME = "users";

    public static final String ID = "_id";
    public static final String USERNAME = "username";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String DATE_BIRTH = "date_birth";
    public static final String GENDER = "gender";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String ADMIN = "is_admin";

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
            + " ("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + USERNAME + " TEXT UNIQUE, "
            + FIRST_NAME + " TEXT, "
            + LAST_NAME + " TEXT, "
            + DATE_BIRTH + " TEXT, "
            + GENDER + " TEXT, "
            + EMAIL + " TEXT UNIQUE, "
            + PASSWORD + " TEXT, "
            + ADMIN + " INTEGER "
            + ");";

    public static final String[] PROJECTION = {
            ID,
            USERNAME,
            FIRST_NAME,
            LAST_NAME,
            DATE_BIRTH,
            GENDER,
            EMAIL,
            PASSWORD,
            ADMIN
    };


}
