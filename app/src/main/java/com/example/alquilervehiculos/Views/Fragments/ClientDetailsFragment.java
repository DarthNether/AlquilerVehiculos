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
import android.widget.TextView;

import com.example.alquilervehiculos.DAO.ClientDAO;
import com.example.alquilervehiculos.DTO.ClientDTO;
import com.example.alquilervehiculos.R;
import com.example.alquilervehiculos.Views.MainViewActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ClientDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ClientDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClientDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ID = "entryId";

    // TODO: Rename and change types of parameters
    private String entryId;

    TextView txtClient;
    TextView txtId;
    TextView txtEmail;
    TextView txtPhone;

    ClientDAO dao;

    private OnFragmentInteractionListener mListener;

    public ClientDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param id Parameter 1.
     * @return A new instance of fragment ClientDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClientDetailsFragment newInstance(String id) {
        ClientDetailsFragment fragment = new ClientDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ID, id);
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
        return inflater.inflate(R.layout.fragment_client_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        txtEmail = view.findViewById(R.id.txt_email);
        txtId = view.findViewById(R.id.txt_id);
        txtPhone = view.findViewById(R.id.txt_phone);
        txtClient = view.findViewById(R.id.txt_client);

        dao = new ClientDAO(getContext());

        new GetClient().execute(entryId);
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

    private class GetClient extends AsyncTask<String, Void, ClientDTO> {

        @Override
        protected ClientDTO doInBackground(String... strings) {
            return dao.getClient(strings[0]);
        }

        @Override
        protected void onPostExecute(ClientDTO clientDTO) {
            String name = clientDTO.getName() + " " + clientDTO.getMiddleName().charAt(0) + ". " + clientDTO.getSurname();

            txtClient.setText(name);
            txtEmail.setText(clientDTO.getEmail());
            txtPhone.setText(clientDTO.getPhoneNumber());
            txtId.setText(clientDTO.getPersonalId());
        }
    }
}
