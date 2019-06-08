package com.example.alquilervehiculos.Views.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;

import com.example.alquilervehiculos.DAO.ClientDAO;
import com.example.alquilervehiculos.DAO.VehicleDAO;
import com.example.alquilervehiculos.DTO.SpinnerClientDTO;
import com.example.alquilervehiculos.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RentFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RentFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ID = "id";

    private String id;
    private VehicleDAO vehicleDAO;
    private ClientDAO clientDAO;
    ArrayAdapter<SpinnerClientDTO> spinnerArrayAdapter;

    Spinner clientSpinner;
    CalendarView rentCalendar;
    Button btnRent;
    Button btnCancel;

    private OnFragmentInteractionListener mListener;

    public RentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param id Parameter 1.
     * @return A new instance of fragment RentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RentFragment newInstance(String id) {
        RentFragment fragment = new RentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getString(ARG_ID);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rent, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        vehicleDAO = new VehicleDAO(view.getContext());
        clientDAO = new ClientDAO(view.getContext());

        btnRent = view.findViewById(R.id.btn_rent);
        btnCancel = view.findViewById(R.id.btn_cancel);

        rentCalendar = view.findViewById(R.id.calendar_rent_date);
        clientSpinner = view.findViewById(R.id.spinner_clients);

        bindEvents();

        spinnerArrayAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, clientDAO.getSpinnerClients());
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerArrayAdapter.notifyDataSetChanged();

        clientSpinner.setAdapter(spinnerArrayAdapter);
    }


    public void bindEvents() {
        btnCancel.setOnClickListener((View v) -> onCancelButtonPressed());
        btnRent.setOnClickListener((View v) -> onRentButtonPressed());
    }

    public void onRentButtonPressed() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String date = dateFormat.format(new Date(rentCalendar.getDate()));

        new RentVehicleTask().execute(this.id, ((SpinnerClientDTO)clientSpinner.getSelectedItem()).getId(), date);
        Objects.requireNonNull(this.getActivity()).onBackPressed();
    }

    public void onCancelButtonPressed() {
        Objects.requireNonNull(this.getActivity()).onBackPressed();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private class RentVehicleTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            vehicleDAO.rentVehicle(strings[0], strings[1], strings[2]);
            return null;
        }
    }
}
