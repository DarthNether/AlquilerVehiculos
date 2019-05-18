package com.example.alquilervehiculos.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.alquilervehiculos.Classes.Utils.DateUtils;
import com.example.alquilervehiculos.DDBB.DatabaseHelper;
import com.example.alquilervehiculos.DTO.LoginUserDTO;

import static com.example.alquilervehiculos.DDBB.DatabaseHelper.COLUMN_CREATED_AT;
import static com.example.alquilervehiculos.DDBB.DatabaseHelper.COLUMN_PASSWORD;
import static com.example.alquilervehiculos.DDBB.DatabaseHelper.COLUMN_STATUS;
import static com.example.alquilervehiculos.DDBB.DatabaseHelper.COLUMN_USERNAME;
import static com.example.alquilervehiculos.DDBB.DatabaseHelper.LOG;
import static com.example.alquilervehiculos.DDBB.DatabaseHelper.TABLE_USERS;

public class UserDAO {
    private DatabaseHelper helper;

    public UserDAO(Context c) {
        helper = DatabaseHelper.getInstance(c);
    }

    public LoginUserDTO saveUser(String username, String password) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_STATUS, "0");
        values.put(COLUMN_CREATED_AT, DateUtils.getDateTime());

        db.insert(TABLE_USERS, null, values);

        return new LoginUserDTO(username, password);
    }

    public LoginUserDTO retrieveUser(String username) {
        SQLiteDatabase db = helper.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " LIKE '" + username + "'";
        Log.e(LOG, query);

        Cursor c = db.rawQuery(query, null);

        LoginUserDTO u = new LoginUserDTO();

        if (c != null && c.getCount() != 0) {
            c.moveToFirst();
            u.setUsername(c.getString(c.getColumnIndex(COLUMN_USERNAME)));
            u.setPassword(c.getString(c.getColumnIndex(COLUMN_PASSWORD)));

            c.close();
        }

        return u;
    }
}
