package com.example.alquilervehiculos.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.alquilervehiculos.Classes.Utils.DateUtils;
import com.example.alquilervehiculos.DDBB.DatabaseHelper;
import com.example.alquilervehiculos.DTO.RecyclerVehicleDTO;

import java.util.ArrayList;
import java.util.List;

import static com.example.alquilervehiculos.DDBB.DatabaseHelper.COLUMN_BRAND;
import static com.example.alquilervehiculos.DDBB.DatabaseHelper.COLUMN_CREATED_AT;
import static com.example.alquilervehiculos.DDBB.DatabaseHelper.COLUMN_ENROLLMENT;
import static com.example.alquilervehiculos.DDBB.DatabaseHelper.COLUMN_MODEL;
import static com.example.alquilervehiculos.DDBB.DatabaseHelper.COLUMN_PRICE_PER_DAY;
import static com.example.alquilervehiculos.DDBB.DatabaseHelper.COLUMN_STATUS;
import static com.example.alquilervehiculos.DDBB.DatabaseHelper.KEY_ID;
import static com.example.alquilervehiculos.DDBB.DatabaseHelper.TABLE_VEHICLES;

public class VehicleDAO {
    private DatabaseHelper helper;

    public VehicleDAO(Context c) {
        helper = DatabaseHelper.getInstance(c);
    }

    public List<RecyclerVehicleDTO> getAllVehicles() {
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] columns = {KEY_ID, COLUMN_ENROLLMENT, COLUMN_BRAND, COLUMN_MODEL};
        String selection = COLUMN_STATUS + " = ?";
        String[] selectionArgs = { "0" };

        Cursor c = db.query(TABLE_VEHICLES, columns, selection, selectionArgs, null, null, null);

        List<RecyclerVehicleDTO> dtos = new ArrayList<>();

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                RecyclerVehicleDTO dto = new RecyclerVehicleDTO();

                dto.setId(c.getString(c.getColumnIndex(KEY_ID)));
                dto.setEnrollment(c.getString(c.getColumnIndex(COLUMN_ENROLLMENT)));
                dto.setBrand(c.getString(c.getColumnIndex(COLUMN_BRAND)));
                dto.setModel(c.getString(c.getColumnIndex(COLUMN_MODEL)));

                dtos.add(dto);

                c.moveToNext();
            }
        }

        c.close();

        return dtos;
    }

    public void saveVehicle(String brand, String model, String enrollment, String price) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_ENROLLMENT, enrollment);
        values.put(COLUMN_BRAND, brand);
        values.put(COLUMN_MODEL, model);
        values.put(COLUMN_PRICE_PER_DAY, price);
        values.put(COLUMN_STATUS, "0");
        values.put(COLUMN_CREATED_AT, DateUtils.getDateTime());

        db.insert(TABLE_VEHICLES, null, values);
    }

    public void removeVehicle(String id) {
        SQLiteDatabase database = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_STATUS, 1);

        String clause = KEY_ID + " =? ";
        String[] args = {id};

        database.update(TABLE_VEHICLES, values, clause, args);
    }

    public void restoreVehicle(String id) {
        SQLiteDatabase database = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_STATUS, 0);

        String clause = KEY_ID + " =? ";
        String[] args = {id};

        database.update(TABLE_VEHICLES, values, clause, args);
    }
}
