package com.example.alquilervehiculos.Views;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.alquilervehiculos.DAO.VehicleDAO;
import com.example.alquilervehiculos.DTO.RecyclerVehicleDTO;
import com.example.alquilervehiculos.R;

import java.util.List;

public class ActivityVehicles extends AppCompatActivity {
    RecyclerView recyclerView;
    VehiclesAdapter adapter;
    VehicleDAO dao;
    List<RecyclerVehicleDTO> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicles);
        dao = new VehicleDAO(getApplicationContext());

        recyclerView = findViewById(R.id.recViewVehicles);

        adapter = new VehiclesAdapter();
        getVehicleData();
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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
            adapter.setDtos(recyclerVehicleDTOS);
            adapter.notifyDataSetChanged();
        }
    }
}
