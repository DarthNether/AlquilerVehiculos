package com.example.alquilervehiculos.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
import static com.example.alquilervehiculos.DDBB.DatabaseHelper.LOG;
import static com.example.alquilervehiculos.DDBB.DatabaseHelper.TABLE_VEHICLES;

public class VehicleDAO {
    private DatabaseHelper helper;

    public VehicleDAO(Context c) {
        helper = DatabaseHelper.getInstance(c);

        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ENROLLMENT, "0572JKL");
        values.put(COLUMN_BRAND, "Opel");
        values.put(COLUMN_MODEL, "Astra");
        values.put(COLUMN_PRICE_PER_DAY, "50");
        values.put(COLUMN_STATUS, "0");
        values.put(COLUMN_CREATED_AT, DateUtils.getDateTime());

        db.insert(TABLE_VEHICLES, null, values);
    }

    public List<RecyclerVehicleDTO> getAllVehicles() {
        SQLiteDatabase db = helper.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_VEHICLES;
        Log.e(LOG, query);

        Cursor c = db.rawQuery(query, null);

        List<RecyclerVehicleDTO> dtos = new ArrayList<>();

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                RecyclerVehicleDTO dto = new RecyclerVehicleDTO();

                dto.setEnrollment(c.getString(c.getColumnIndex(COLUMN_ENROLLMENT)));
                dto.setBrand(c.getString(c.getColumnIndex(COLUMN_BRAND)));
                dto.setBrand(c.getString(c.getColumnIndex(COLUMN_MODEL)));

                dtos.add(dto);
            }
        }

        return dtos;
    }
}
