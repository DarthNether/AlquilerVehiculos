package com.example.alquilervehiculos.Views;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.alquilervehiculos.DAO.VehicleDAO;
import com.example.alquilervehiculos.DTO.RecyclerVehicleDTO;
import com.example.alquilervehiculos.R;

import java.util.ArrayList;
import java.util.List;

public class ActivityVehicles extends AppCompatActivity {
    RecyclerView recyclerView;

    VehicleDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicles);

        recyclerView = findViewById(R.id.recViewVehicles);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        dao = new VehicleDAO(getApplicationContext());
        getVehicleData();
    }

    private void getVehicleData() {
        new MyTask().execute();
    }

    private class MyTask extends AsyncTask<Void, Void, List<RecyclerVehicleDTO>> {

        @Override
        protected List<RecyclerVehicleDTO> doInBackground(Void... voids) {
            return dao.getAllVehicles();
        }

        @Override
        protected void onPostExecute(List<RecyclerVehicleDTO> recyclerVehicleDTOS) {
            recyclerView.setAdapter(new VehiclesAdapter(new ArrayList<RecyclerVehicleDTO>()));
        }
    }
}
