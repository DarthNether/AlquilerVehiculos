package com.example.alquilervehiculos.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.alquilervehiculos.Classes.Utils.DateUtils;
import com.example.alquilervehiculos.DDBB.DatabaseHelper;
import com.example.alquilervehiculos.DTO.ClientDTO;
import com.example.alquilervehiculos.DTO.RecyclerClientDTO;
import com.example.alquilervehiculos.DTO.SpinnerClientDTO;

import java.util.ArrayList;
import java.util.List;

import static com.example.alquilervehiculos.DDBB.DatabaseHelper.COLUMN_CREATED_AT;
import static com.example.alquilervehiculos.DDBB.DatabaseHelper.COLUMN_EMAIL;
import static com.example.alquilervehiculos.DDBB.DatabaseHelper.COLUMN_MIDDLE_NAME;
import static com.example.alquilervehiculos.DDBB.DatabaseHelper.COLUMN_NAME;
import static com.example.alquilervehiculos.DDBB.DatabaseHelper.COLUMN_PERSONAL_ID;
import static com.example.alquilervehiculos.DDBB.DatabaseHelper.COLUMN_PHONE_NUMBER;
import static com.example.alquilervehiculos.DDBB.DatabaseHelper.COLUMN_STATUS;
import static com.example.alquilervehiculos.DDBB.DatabaseHelper.COLUMN_SURNAME;
import static com.example.alquilervehiculos.DDBB.DatabaseHelper.KEY_ID;
import static com.example.alquilervehiculos.DDBB.DatabaseHelper.TABLE_CLIENTS;

public class ClientDAO {

    private DatabaseHelper helper;

    public ClientDAO(Context c) {
        helper = DatabaseHelper.getInstance(c);
    }

    public List<RecyclerClientDTO> getAllClients() {
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] columns = {KEY_ID, COLUMN_NAME, COLUMN_MIDDLE_NAME, COLUMN_SURNAME, COLUMN_PERSONAL_ID};
        String selection = COLUMN_STATUS + " = ?";
        String[] selectionArgs = {"0"};
        String sortOrder = COLUMN_SURNAME + " ASC";

        Cursor c = db.query(TABLE_CLIENTS, columns, selection, selectionArgs, null, null, sortOrder);
        List<RecyclerClientDTO> dtos = new ArrayList<>();

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                RecyclerClientDTO dto = new RecyclerClientDTO();

                dto.setId(c.getString(c.getColumnIndex(KEY_ID)));
                dto.setName(c.getString(c.getColumnIndex(COLUMN_NAME)));
                dto.setMiddleName(c.getString(c.getColumnIndex(COLUMN_MIDDLE_NAME)));
                dto.setSurname(c.getString(c.getColumnIndex(COLUMN_SURNAME)));
                dto.setPersonalId(c.getString(c.getColumnIndex(COLUMN_PERSONAL_ID)));

                dtos.add(dto);

                c.moveToNext();
            }
        }

        c.close();

        return dtos;
    }

    public ArrayList<SpinnerClientDTO> getSpinnerClients() {
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] columns = {KEY_ID, COLUMN_NAME, COLUMN_MIDDLE_NAME, COLUMN_SURNAME, COLUMN_PERSONAL_ID};
        String selection = COLUMN_STATUS + " = ?";
        String[] selectionArgs = {"0"};
        String sortOrder = COLUMN_SURNAME + " ASC";

        Cursor c = db.query(TABLE_CLIENTS, columns, selection, selectionArgs, null, null, sortOrder);
        ArrayList<SpinnerClientDTO> dtos = new ArrayList<>();

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                SpinnerClientDTO dto = new SpinnerClientDTO();

                dto.setId(c.getString(c.getColumnIndex(KEY_ID)));
                dto.setName(c.getString(c.getColumnIndex(COLUMN_NAME)));
                dto.setMiddleName(c.getString(c.getColumnIndex(COLUMN_MIDDLE_NAME)));
                dto.setSurname(c.getString(c.getColumnIndex(COLUMN_SURNAME)));

                dtos.add(dto);

                c.moveToNext();
            }
        }

        c.close();

        return dtos;
    }

    public void saveClient(String name, String middleName, String surname, String personalID, String phone, String email) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_PERSONAL_ID, personalID);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_MIDDLE_NAME, middleName);
        values.put(COLUMN_SURNAME, surname);
        values.put(COLUMN_PHONE_NUMBER, phone);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_STATUS, "0");
        values.put(COLUMN_CREATED_AT, DateUtils.getDateTime());

        db.insert(TABLE_CLIENTS, null, values);
    }

    public void updateClient(String id, String name, String middleName, String surname, String personalID, String phone, String email) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_PERSONAL_ID, personalID);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_MIDDLE_NAME, middleName);
        values.put(COLUMN_SURNAME, surname);
        values.put(COLUMN_PHONE_NUMBER, phone);
        values.put(COLUMN_EMAIL, email);

        String clause = KEY_ID + " =? ";
        String[] args = {id};

        db.update(TABLE_CLIENTS, values, clause, args);
    }

    public void removeClient(String id) {
        SQLiteDatabase database = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_STATUS, 1);

        String clause = KEY_ID + " =? ";
        String[] args = {id};

        database.update(TABLE_CLIENTS, values, clause, args);
    }

    public void restoreClient(String id) {
        SQLiteDatabase database = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_STATUS, 0);

        String clause = KEY_ID + " =? ";
        String[] args = {id};

        database.update(TABLE_CLIENTS, values, clause, args);
    }

    public ClientDTO getClient(String id) {
        SQLiteDatabase database = helper.getReadableDatabase();

        String[] columns = {COLUMN_PERSONAL_ID, COLUMN_NAME, COLUMN_MIDDLE_NAME, COLUMN_SURNAME, COLUMN_PHONE_NUMBER, COLUMN_EMAIL};
        String clause = KEY_ID + " =? ";
        String[] args = {id};

        Cursor c = database.query(TABLE_CLIENTS, columns, clause, args, null, null, null);
        ClientDTO client = new ClientDTO();

        if (c.moveToFirst()) {
            client.setPersonalId(c.getString(c.getColumnIndex(COLUMN_PERSONAL_ID)));
            client.setSurname(c.getString(c.getColumnIndex(COLUMN_SURNAME)));
            client.setPhoneNumber(c.getString(c.getColumnIndex(COLUMN_PHONE_NUMBER)));
            client.setName(c.getString(c.getColumnIndex(COLUMN_NAME)));
            client.setMiddleName(c.getString(c.getColumnIndex(COLUMN_MIDDLE_NAME)));
            client.setEmail(c.getString(c.getColumnIndex(COLUMN_EMAIL)));
        }

        c.close();

        return client;
    }
}
