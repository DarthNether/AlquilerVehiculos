package com.example.alquilervehiculos.Views;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alquilervehiculos.DTO.RecyclerClientDTO;
import com.example.alquilervehiculos.R;

import java.util.ArrayList;
import java.util.List;

public class ClientsAdapter extends RecyclerView.Adapter<ClientsAdapter.ClientsViewHolder> {
    private List<RecyclerClientDTO> dtos = new ArrayList<>();

    public List<RecyclerClientDTO> getDtos() {
        return dtos;
    }

    public void setDtos(List<RecyclerClientDTO> dtos) {
        this.dtos = dtos;
    }

    @NonNull
    @Override
    public ClientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_client, parent, false);

        return new ClientsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientsViewHolder clientsViewHolder, int i) {
        clientsViewHolder.setData(dtos.get(i));
    }

    @Override
    public int getItemCount() {
        return dtos.size();
    }

    static class ClientsViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView middleName;
        TextView surname;
        TextView personalId;

        ClientsViewHolder(View v) {
            super(v);

            name = v.findViewById(R.id.name);
            middleName = v.findViewById(R.id.middle_name);
            surname = v.findViewById(R.id.surname);
            personalId = v.findViewById(R.id.personal_id);
        }

        void setData(RecyclerClientDTO data) {
            name.setText(data.getName());
            middleName.setText(String.format("%s.", data.getMiddleName().charAt(0)));
            surname.setText(String.format("%s,", data.getSurname()));
            personalId.setText(data.getPersonalId());
        }
    }
}
