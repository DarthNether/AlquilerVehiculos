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

import com.example.alquilervehiculos.DAO.ClientDAO;
import com.example.alquilervehiculos.R;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditClientFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditClientFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditClientFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ID = "id";

    // TODO: Rename and change types of parameters
    private String id;

    ClientDAO dao;

    EditText txtName;
    EditText txtMiddleName;
    EditText txtSurname;
    EditText txtId;
    EditText txtPhone;
    EditText txtEmail;

    private OnFragmentInteractionListener mListener;

    public EditClientFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param id Parameter 1.
     * @return A new instance of fragment EditClientFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditClientFragment newInstance(String id) {
        EditClientFragment fragment = new EditClientFragment();
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
        return inflater.inflate(R.layout.fragment_edit_client, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Button btnSave = view.findViewById(R.id.btn_save);
        Button btnCancel = view.findViewById(R.id.btn_cancel);
        txtName = view.findViewById(R.id.txt_name);
        txtSurname = view.findViewById(R.id.txt_surname);
        txtMiddleName = view.findViewById(R.id.txt_middle_name);
        txtEmail = view.findViewById(R.id.txt_email);
        txtPhone = view.findViewById(R.id.txt_phone);
        txtId = view.findViewById(R.id.txt_id);

        dao = new ClientDAO(view.getContext());

        btnSave.setOnClickListener(v -> onSaveButtonPressed());
        btnCancel.setOnClickListener(v -> onCancelButtonPressed());
    }

    public void onSaveButtonPressed() {
        String[] data = new String[6];

        if (txtName.getText().toString().isEmpty()) {
            txtName.setError("This field must not be empty");
        } else if (txtMiddleName.getText().toString().isEmpty()) {
            txtMiddleName.setError("This field must not be empty");
        } else if (txtSurname.getText().toString().isEmpty()) {
            txtSurname.setError("This field must not be empty");
        } else if (txtId.getText().toString().isEmpty()) {
            txtId.setError("This field must not be empty");
        } else if (txtPhone.getText().toString().isEmpty()) {
            txtPhone.setError("This field must not be empty");
        } else if (txtEmail.getText().toString().isEmpty()) {
            txtEmail.setError("This field must not be empty");
        } else if (!txtId.getText().toString().matches("[0-9]{7,8}[TRWAGMYFPDXBNJZSQVHLCKEtrwagmyfpdxbnjzsqvhlcke]")) {
            txtId.setError("Not a valid Spanish ID");
        } else if (!txtPhone.getText().toString().matches("^(\\+34|0034|34)?[6|7|8|9][0-9]{8}$")
                && !txtPhone.getText().toString().matches("^(\\+34|0034|34)?[\\s|\\-|.]?[6|7|8|9][\\s|\\-|.]?([0-9][\\s|\\-|.]?){8}$")) {
            txtPhone.setError("Not a valid phone number");
        } else if (!txtEmail.getText().toString().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            txtEmail.setError("Not a valid email address");
        } else {
            data[0] = txtName.getText().toString();
            data[1] = txtMiddleName.getText().toString();
            data[2] = txtSurname.getText().toString();
            data[3] = txtId.getText().toString();
            data[4] = txtPhone.getText().toString();
            data[5] = txtEmail.getText().toString();

            new UpdateClient().execute(data);
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

    private class UpdateClient extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            dao.updateClient(id, strings[0], strings[1], strings[2], strings[3], strings[4], strings[5]);
            return null;
        }
    }
}
