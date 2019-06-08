package com.example.alquilervehiculos.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.alquilervehiculos.Classes.Utils.DateUtils;
import com.example.alquilervehiculos.DDBB.DatabaseHelper;
import com.example.alquilervehiculos.DTO.RecyclerVehicleDTO;
import com.example.alquilervehiculos.DTO.VehicleDTO;

import java.util.ArrayList;
import java.util.List;

import static com.example.alquilervehiculos.DDBB.DatabaseHelper.COLUMN_BRAND;
import static com.example.alquilervehiculos.DDBB.DatabaseHelper.COLUMN_CLIENT_ID;
import static com.example.alquilervehiculos.DDBB.DatabaseHelper.COLUMN_CREATED_AT;
import static com.example.alquilervehiculos.DDBB.DatabaseHelper.COLUMN_ENROLLMENT;
import static com.example.alquilervehiculos.DDBB.DatabaseHelper.COLUMN_MODEL;
import static com.example.alquilervehiculos.DDBB.DatabaseHelper.COLUMN_PRICE_PER_DAY;
import static com.example.alquilervehiculos.DDBB.DatabaseHelper.COLUMN_RENTED;
import static com.example.alquilervehiculos.DDBB.DatabaseHelper.COLUMN_RENT_DATE;
import static com.example.alquilervehiculos.DDBB.DatabaseHelper.COLUMN_RETURN_DATE;
import static com.example.alquilervehiculos.DDBB.DatabaseHelper.COLUMN_STATUS;
import static com.example.alquilervehiculos.DDBB.DatabaseHelper.COLUMN_VEHICLE_ID;
import static com.example.alquilervehiculos.DDBB.DatabaseHelper.KEY_ID;
import static com.example.alquilervehiculos.DDBB.DatabaseHelper.TABLE_RENTS;
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
        String sortOrder = COLUMN_BRAND + " ASC";

        Cursor c = db.query(TABLE_VEHICLES, columns, selection, selectionArgs, null, null, sortOrder);

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

    public VehicleDTO getVehicle(String id) {
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] columns = {COLUMN_ENROLLMENT, COLUMN_BRAND, COLUMN_MODEL, COLUMN_RENTED, COLUMN_PRICE_PER_DAY};
        String selection = KEY_ID + " = ?";
        String[] selectionArgs = {id};

        Cursor c = db.query(TABLE_VEHICLES, columns, selection, selectionArgs, null, null, null);

        VehicleDTO dto = new VehicleDTO();

        if (c.moveToFirst()) {
            dto.setId(id);
            dto.setEnrollment(c.getString(c.getColumnIndex(COLUMN_ENROLLMENT)));
            dto.setBrand(c.getString(c.getColumnIndex(COLUMN_BRAND)));
            dto.setModel(c.getString(c.getColumnIndex(COLUMN_MODEL)));
            dto.setRented(c.getString(c.getColumnIndex(COLUMN_RENTED)));
            dto.setPrice_day(c.getString(c.getColumnIndex(COLUMN_PRICE_PER_DAY)));
        }

        c.close();

        return dto;
    }

    public void saveVehicle(String brand, String model, String enrollment, String price) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_ENROLLMENT, enrollment);
        values.put(COLUMN_BRAND, brand);
        values.put(COLUMN_MODEL, model);
        values.put(COLUMN_PRICE_PER_DAY, price);
        values.put(COLUMN_RENTED, "0");
        values.put(COLUMN_STATUS, "0");
        values.put(COLUMN_CREATED_AT, DateUtils.getDateTime());

        db.insert(TABLE_VEHICLES, null, values);
    }

    public void updateVehicle(String id, String brand, String model, String enrollment, String price) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_ENROLLMENT, enrollment);
        values.put(COLUMN_BRAND, brand);
        values.put(COLUMN_MODEL, model);
        values.put(COLUMN_PRICE_PER_DAY, price);

        String clause = KEY_ID + " =? ";
        String[] args = {id};

        db.update(TABLE_VEHICLES, values, clause, args);
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

    public void rentVehicle(String vehicleId, String clientId, String date) {
        SQLiteDatabase database = helper.getWritableDatabase();

        ContentValues rentValues = new ContentValues();
        rentValues.put(COLUMN_CLIENT_ID, clientId);
        rentValues.put(COLUMN_VEHICLE_ID, vehicleId);
        rentValues.put(COLUMN_RENT_DATE, date);

        database.insert(TABLE_RENTS, null, rentValues);

        ContentValues vehicleValues = new ContentValues();
        vehicleValues.put(COLUMN_RENTED, 1);

        String clause = KEY_ID + " =? ";
        String[] args = {vehicleId};

        database.update(TABLE_VEHICLES, vehicleValues, clause, args);
    }

    public void returnVehicle(String vehicleId, String date) {
        SQLiteDatabase database = helper.getWritableDatabase();

        ContentValues vehicleValues = new ContentValues();
        vehicleValues.put(COLUMN_RENTED, "0");

        String vehicleClause = KEY_ID + " =? ";
        String[] vehicleArgs = {vehicleId};

        database.update(TABLE_VEHICLES, vehicleValues, vehicleClause, vehicleArgs);

        ContentValues rentValues = new ContentValues();
        rentValues.put(COLUMN_RETURN_DATE, date);

        String rentClause = COLUMN_VEHICLE_ID + " =? AND " + COLUMN_RETURN_DATE + " =? ";
        String[] rentArgs = {vehicleId, "IS NULL"};

        database.update(TABLE_RENTS, rentValues, rentClause, rentArgs);
    }
}
