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
import android.widget.EditText;

import com.example.alquilervehiculos.DAO.VehicleDAO;
import com.example.alquilervehiculos.R;
import com.example.alquilervehiculos.Views.MainViewActivity;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewVehicleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewVehicleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditVehicleFragment extends Fragment {
    private static final String ARG_ID = "id";

    private String id;

    private OnFragmentInteractionListener mListener;

    private EditText txtBrand;
    private EditText txtModel;
    private EditText txtEnrollment;
    private EditText txtPrice;

    VehicleDAO dao;

    public EditVehicleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param id Parameter 1.
     * @return A new instance of fragment NewVehicleFragment.
     */
    public static EditVehicleFragment newInstance(String id) {
        EditVehicleFragment fragment = new EditVehicleFragment();
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
        return inflater.inflate(R.layout.fragment_edit_vehicle, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Button btnSave = view.findViewById(R.id.btn_save);
        Button btnCancel = view.findViewById(R.id.btn_cancel);
        txtEnrollment = view.findViewById(R.id.txt_enrollment);
        txtBrand = view.findViewById(R.id.txt_brand);
        txtModel = view.findViewById(R.id.txt_model);
        txtPrice = view.findViewById(R.id.txt_price);

        dao = new VehicleDAO(view.getContext());

        btnSave.setOnClickListener(v -> onSaveButtonPressed());
        btnCancel.setOnClickListener(v -> onCancelButtonPressed());
    }

    public void onSaveButtonPressed() {
        String[] data = new String[4];

        if (txtBrand.getText().toString().isEmpty()) {
            txtBrand.setError("This field must not be empty");
        } else if (txtModel.getText().toString().isEmpty()) {
            txtModel.setError("This field must not be empty");
        } else if (txtEnrollment.getText().toString().isEmpty()) {
            txtEnrollment.setError("This field must not be empty");
        } else if (txtPrice.getText().toString().isEmpty()) {
            txtPrice.setError("This field must not be empty");
        } else if (!txtEnrollment.getText().toString().matches("(\\d{4})([A-Z]{3})")
                && !txtEnrollment.getText().toString().matches("([A-Z]{1,2})(\\d{4})([A-Z]{0,2})")) {
            txtEnrollment.setError("Not a valid enrollment");
        } else if (!txtPrice.getText().toString().matches("(\\d+\\.\\d{1,2})")) {
            txtPrice.setError("Not a valid price");
        } else {
            data[0] = txtBrand.getText().toString();
            data[1] = txtModel.getText().toString();
            data[2] = txtEnrollment.getText().toString();
            data[3] = txtPrice.getText().toString();

            new UpdateVehicleTask().execute(data);
        }

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
        ((MainViewActivity) getActivity()).changeFABIcon(R.drawable.ic_add_white_24dp);
    }

    public void setmListener(OnFragmentInteractionListener mListener) {
        this.mListener = mListener;
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

    private class UpdateVehicleTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            dao.updateVehicle(id, strings[0], strings[1], strings[2], strings[3]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Objects.requireNonNull(EditVehicleFragment.this.getActivity()).onBackPressed();
        }
    }
}
