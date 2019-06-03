package com.example.alquilervehiculos.Views;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alquilervehiculos.DTO.RecyclerVehicleDTO;
import com.example.alquilervehiculos.R;

import java.util.ArrayList;
import java.util.List;

public class VehiclesAdapter extends RecyclerView.Adapter<VehiclesAdapter.VehiclesViewHolder> {
    private List<RecyclerVehicleDTO> dtos = new ArrayList<>();

    public List<RecyclerVehicleDTO> getDtos() {
        return dtos;
    }

    public void setDtos(List<RecyclerVehicleDTO> dtos) {
        this.dtos = dtos;
    }

    @NonNull
    @Override
    public VehiclesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vehicle, parent, false);

        return new VehiclesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VehiclesViewHolder vehiclesViewHolder, int i) {
        vehiclesViewHolder.setData(dtos.get(i));
    }

    @Override
    public int getItemCount() {
        return dtos.size();
    }


    public RecyclerVehicleDTO getVehicle(int position) {
        return dtos.get(position);
    }

    static class VehiclesViewHolder extends RecyclerView.ViewHolder {
        TextView brand;
        TextView model;
        TextView enrollment;

        VehiclesViewHolder(View v) {
            super(v);

            brand = v.findViewById(R.id.Brand);
            model = v.findViewById(R.id.Model);
            enrollment = v.findViewById(R.id.Enrollment);
        }

        void setData(RecyclerVehicleDTO data) {
            brand.setText(data.getBrand());
            model.setText(data.getModel());
            enrollment.setText(data.getEnrollment());
        }
    }


}
