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
import android.widget.Button;
import android.widget.CalendarView;

import com.example.alquilervehiculos.DAO.VehicleDAO;
import com.example.alquilervehiculos.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReturnVehicleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReturnVehicleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReturnVehicleFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ID = "id";

    // TODO: Rename and change types of parameters
    private String id;
    private VehicleDAO vehicleDAO;

    Button btnReturn;
    Button btnCancel;
    CalendarView calendar;

    private OnFragmentInteractionListener mListener;

    public ReturnVehicleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param id Parameter 1.
     * @return A new instance of fragment ReturnVehicleFragment.
     */
    public static ReturnVehicleFragment newInstance(String id) {
        ReturnVehicleFragment fragment = new ReturnVehicleFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_return_vehicle, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        btnCancel = view.findViewById(R.id.btn_cancel);
        btnReturn = view.findViewById(R.id.btn_return);
        calendar = view.findViewById(R.id.calendar_return_date);

        vehicleDAO = new VehicleDAO(view.getContext());

        bindEvents();
    }

    public void bindEvents() {
        btnReturn.setOnClickListener((View v) -> onReturnButtonPressed());
        btnCancel.setOnClickListener((View v) -> onCancelButtonPressed());
    }

    public void onReturnButtonPressed() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String date = dateFormat.format(new Date(calendar.getDate()));

        new ReturnVehicle().execute(id, date);
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

    private class ReturnVehicle extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            vehicleDAO.returnVehicle(strings[0], strings[1]);
            return null;
        }
    }
}
