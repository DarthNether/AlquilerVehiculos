package com.example.alquilervehiculos.DDBB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Log
    public static final String LOG = DatabaseHelper.class.getName();

    //Database Version
    private static final int DATABASE_VERSION = 1;

    //Database Name
    private static final String DATABASE_NAME = "alquilerVehiculos";

    //Table names
    public static final String TABLE_USERS = "users";
    public static final String TABLE_VEHICLES = "vehicles";

    //Common column names
    public static final String KEY_ID = "id";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_CREATED_AT = "created_at";

    //USERS table column names
    public static final String COLUMN_USERNAME = "user_name";
    public static final String COLUMN_PASSWORD = "password";

    //VEHICLES table column names
    public static final String COLUMN_ENROLLMENT = "enrollment";
    public static final String COLUMN_BRAND = "brand";
    public static final String COLUMN_MODEL = "model";
    public static final String COLUMN_PRICE_PER_DAY = "price_day";
    public static final String COLUMN_RENTED = "rented";

    //Table create statements
    //USER table create statement
    private static final String CREATE_TABLE_USERS = "CREATE TABLE "
            + TABLE_USERS + " ("
            + KEY_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_USERNAME + " TEXT,"
            + COLUMN_PASSWORD + " TEXT,"
            + COLUMN_STATUS + " INTEGER,"
            + COLUMN_CREATED_AT + " DATETIME"
            + ")";

    //VEHICLE table crate statement
    private static final String CREATE_TABLE_VEHICLES = "CREATE TABLE "
            + TABLE_VEHICLES + " ("
            + KEY_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_ENROLLMENT + " TEXT,"
            + COLUMN_BRAND + " TEXT,"
            + COLUMN_MODEL + " TEXT,"
            + COLUMN_PRICE_PER_DAY + " DOUBLE,"
            + COLUMN_RENTED + " INTEGER,"
            + COLUMN_STATUS + " DATETIME"
            + ")";

    //VEHICLES TABLE default INSERTS


    private static DatabaseHelper instance;

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DatabaseHelper getInstance(Context c) {
        if (instance == null) {
            instance = new DatabaseHelper(c);
        }

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_USERS);
        sqLiteDatabase.execSQL(CREATE_TABLE_VEHICLES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(sqLiteDatabase);
    }
}