package com.example.alquilervehiculos.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.alquilervehiculos.DDBB.DatabaseHelper;
import com.example.alquilervehiculos.DTO.RecyclerVehicleDTO;

import java.util.ArrayList;
import java.util.List;

import static com.example.alquilervehiculos.DDBB.DatabaseHelper.COLUMN_BRAND;
import static com.example.alquilervehiculos.DDBB.DatabaseHelper.COLUMN_ENROLLMENT;
import static com.example.alquilervehiculos.DDBB.DatabaseHelper.COLUMN_MODEL;
import static com.example.alquilervehiculos.DDBB.DatabaseHelper.COLUMN_STATUS;
import static com.example.alquilervehiculos.DDBB.DatabaseHelper.TABLE_VEHICLES;

public class VehicleDAO {
    private DatabaseHelper helper;

    public VehicleDAO(Context c) {
        helper = DatabaseHelper.getInstance(c);
    }

    public List<RecyclerVehicleDTO> getAllVehicles() {
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] columns = {COLUMN_ENROLLMENT, COLUMN_BRAND, COLUMN_MODEL};
        String selection = COLUMN_STATUS + " = ?";
        String[] selectionArgs = { "0" };

        Cursor c = db.query(TABLE_VEHICLES, columns, selection, selectionArgs, null, null, null);

        List<RecyclerVehicleDTO> dtos = new ArrayList<>();

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                RecyclerVehicleDTO dto = new RecyclerVehicleDTO();

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
}
