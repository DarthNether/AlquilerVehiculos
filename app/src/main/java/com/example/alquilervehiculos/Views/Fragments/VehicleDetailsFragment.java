package com.example.alquilervehiculos.Views.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alquilervehiculos.DAO.VehicleDAO;
import com.example.alquilervehiculos.DTO.VehicleDTO;
import com.example.alquilervehiculos.R;
import com.example.alquilervehiculos.Views.MainViewActivity;
import com.google.android.gms.maps.MapView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VehicleDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VehicleDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VehicleDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ID = "entryId";

    private String entryId;

    TextView txtEnrollment;
    TextView txtBrand;
    TextView txtModel;
    TextView txtPrice;
    Button btnRent;
    MapView mapVehicleLocation;

    private OnFragmentInteractionListener mListener;

    VehicleDAO dao;
    GetVehicleTask getVehicleTask;


    public VehicleDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment VehicleDetailsFragment.
     */
    public static VehicleDetailsFragment newInstance(String param1) {
        VehicleDetailsFragment fragment = new VehicleDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ID, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            entryId = getArguments().getString(ARG_ID);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainViewActivity) getActivity()).changeFABIcon(R.drawable.ic_edit_black_24dp);
        return inflater.inflate(R.layout.fragment_vehicle_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        btnRent = view.findViewById(R.id.btn_return);
        txtBrand = view.findViewById(R.id.txt_brand);
        txtEnrollment = view.findViewById(R.id.txt_enrollment);
        txtPrice = view.findViewById(R.id.txt_price);
        txtModel = view.findViewById(R.id.txt_model);
        mapVehicleLocation = view.findViewById(R.id.map_vehicle_location);

        dao = new VehicleDAO(view.getContext());

        getVehicleTask = new GetVehicleTask();
        getVehicleTask.execute(entryId);

        bindEvents();
    }

    public void bindEvents() {
        btnRent.setOnClickListener((View v) -> onRentPress());
    }

    public void onRentPress() {
        if (btnRent.getText().toString().equals("RENT")) {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.dynamic_fragment_layout, RentFragment.newInstance(entryId));
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (btnRent.getText().toString().equals("RETURN")) {
            new ReturnVehicle().execute(entryId);
            new GetVehicleTask().execute(entryId);

            Toast.makeText(getContext(), "Vehicle successfully returned.", Toast.LENGTH_SHORT).show();
        }
    }

    public String getEntryId() {
        return entryId;
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

    private class GetVehicleTask extends AsyncTask<String, Void, VehicleDTO> {

        @Override
        protected VehicleDTO doInBackground(String... strings) {
            return dao.getVehicle(strings[0]);
        }

        @Override
        protected void onPostExecute(VehicleDTO dto) {
            txtBrand.setText(dto.getBrand());
            txtModel.setText(dto.getModel());
            String price = dto.getPrice_day() + " €/day";
            txtPrice.setText(price);
            txtEnrollment.setText(dto.getEnrollment());

            if (dto.getRented().equals("0")) {
                btnRent.setText(R.string.rent_vehicle);
            } else {
                btnRent.setText(R.string.return_vehicle);
            }
        }
    }

    private class ReturnVehicle extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            dao.returnVehicle(strings[0]);
            return null;
        }
    }
}
