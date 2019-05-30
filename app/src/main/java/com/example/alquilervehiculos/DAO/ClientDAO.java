package com.example.alquilervehiculos.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.alquilervehiculos.DDBB.DatabaseHelper;
import com.example.alquilervehiculos.DTO.RecyclerClientDTO;

import java.util.ArrayList;
import java.util.List;

import static com.example.alquilervehiculos.DDBB.DatabaseHelper.COLUMN_MIDDLE_NAME;
import static com.example.alquilervehiculos.DDBB.DatabaseHelper.COLUMN_NAME;
import static com.example.alquilervehiculos.DDBB.DatabaseHelper.COLUMN_PERSONAL_ID;
import static com.example.alquilervehiculos.DDBB.DatabaseHelper.COLUMN_STATUS;
import static com.example.alquilervehiculos.DDBB.DatabaseHelper.COLUMN_SURNAME;
import static com.example.alquilervehiculos.DDBB.DatabaseHelper.TABLE_CLIENTS;

public class ClientDAO {

    private DatabaseHelper helper;

    public ClientDAO(Context c) {
        helper = DatabaseHelper.getInstance(c);
    }

    public List<RecyclerClientDTO> getAllClients() {
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] columns = {COLUMN_NAME, COLUMN_MIDDLE_NAME, COLUMN_SURNAME, COLUMN_PERSONAL_ID};
        String selection = COLUMN_STATUS + " = ?";
        String[] selectionArgs = {"0"};
        String sortOrder = COLUMN_SURNAME + " DESC";

        Cursor c = db.query(TABLE_CLIENTS, columns, selection, selectionArgs, null, null, sortOrder);
        List<RecyclerClientDTO> dtos = new ArrayList<>();

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                RecyclerClientDTO dto = new RecyclerClientDTO();

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
}
