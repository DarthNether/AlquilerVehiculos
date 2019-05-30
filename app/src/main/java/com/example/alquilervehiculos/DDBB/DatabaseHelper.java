package com.example.alquilervehiculos.DDBB;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.alquilervehiculos.Classes.Utils.DateUtils;

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
    public static final String TABLE_CLIENTS = "clients";

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

    //CLIENTS table column names
    public static final String COLUMN_PERSONAL_ID = "personal_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_MIDDLE_NAME = "middle_name";
    public static final String COLUMN_SURNAME = "surname";
    public static final String COLUMN_PHONE_NUMBER = "phone_number";
    public static final String COLUMN_EMAIL = "email";

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

    //VEHICLE table create statement
    private static final String CREATE_TABLE_VEHICLES = "CREATE TABLE "
            + TABLE_VEHICLES + " ("
            + KEY_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_ENROLLMENT + " TEXT,"
            + COLUMN_BRAND + " TEXT,"
            + COLUMN_MODEL + " TEXT,"
            + COLUMN_PRICE_PER_DAY + " DOUBLE,"
            + COLUMN_RENTED + " INTEGER,"
            + COLUMN_STATUS + " INTEGER,"
            + COLUMN_CREATED_AT + " DATETIME"
            + ")";

    //CLIENTS table create statement
    private static final String CREATE_TABLE_CLIENTS = "CREATE TABLE "
            + TABLE_CLIENTS + " ("
            + KEY_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_PERSONAL_ID + " TEXT,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_MIDDLE_NAME + " TEXT,"
            + COLUMN_SURNAME + " TEXT,"
            + COLUMN_PHONE_NUMBER + " TEXT,"
            + COLUMN_EMAIL + " TEXT,"
            + COLUMN_STATUS + " INTEGER,"
            + COLUMN_CREATED_AT + " DATETIME"
            + ")";

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
        sqLiteDatabase.execSQL(CREATE_TABLE_CLIENTS);

        //Default insert VEHICLES
        ContentValues values = new ContentValues();
        values.put(COLUMN_ENROLLMENT, "0572JKL");
        values.put(COLUMN_BRAND, "Opel");
        values.put(COLUMN_MODEL, "Astra");
        values.put(COLUMN_PRICE_PER_DAY, "50");
        values.put(COLUMN_STATUS, "0");
        values.put(COLUMN_CREATED_AT, DateUtils.getDateTime());

        sqLiteDatabase.insert(TABLE_VEHICLES, null, values);

        //Default insert CLIENTS
        values = new ContentValues();
        values.put(COLUMN_PERSONAL_ID, "98767645K");
        values.put(COLUMN_NAME, "Homer");
        values.put(COLUMN_MIDDLE_NAME, "Jay");
        values.put(COLUMN_SURNAME, "Simpson");
        values.put(COLUMN_PHONE_NUMBER, "777555999");
        values.put(COLUMN_EMAIL, "homer@springfield.com");
        values.put(COLUMN_STATUS, "0");
        values.put(COLUMN_CREATED_AT, DateUtils.getDateTime());

        sqLiteDatabase.insert(TABLE_CLIENTS, null, values);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_VEHICLES);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENTS);
        onCreate(sqLiteDatabase);
    }
}