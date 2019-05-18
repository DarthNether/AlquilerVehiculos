package com.example.alquilervehiculos.Views;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alquilervehiculos.DTO.RecyclerVehicleDTO;
import com.example.alquilervehiculos.R;

import java.util.List;

public class VehiclesAdapter extends RecyclerView.Adapter<VehiclesAdapter.VehiclesViewHolder> {
    private List<RecyclerVehicleDTO> dtos;

    public static class VehiclesViewHolder extends RecyclerView.ViewHolder {
        public TextView view;

        public VehiclesViewHolder(TextView v) {
            super(v);
            view = v;
        }
    }

    public VehiclesAdapter(List<RecyclerVehicleDTO> dtos) {
        this.dtos = dtos;
    }

    @NonNull
    @Override
    public VehiclesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_vehicles, parent, false);

        VehiclesViewHolder vh = new VehiclesViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull VehiclesViewHolder vehiclesViewHolder, int i) {
        vehiclesViewHolder.view.setText(dtos.get(i).getEnrollment());
    }

    @Override
    public int getItemCount() {
        return dtos.size();
    }
}
