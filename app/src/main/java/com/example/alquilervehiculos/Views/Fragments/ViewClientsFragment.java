package com.example.alquilervehiculos.Views.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alquilervehiculos.DAO.ClientDAO;
import com.example.alquilervehiculos.DTO.RecyclerClientDTO;
import com.example.alquilervehiculos.R;
import com.example.alquilervehiculos.Views.ClientsAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewClientsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewClientsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewClientsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    RecyclerView recyclerClients;
    ClientsAdapter adapter;
    ClientDAO dao;
    MyTask task;

    public ViewClientsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewClientsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewClientsFragment newInstance(String param1, String param2) {
        ViewClientsFragment fragment = new ViewClientsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_clients, container, false);

        recyclerClients = view.findViewById(R.id.recViewClients);

        dao = new ClientDAO(getContext());

        adapter = new ClientsAdapter();
        getClientsData();
        recyclerClients.setAdapter(adapter);

        recyclerClients.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    private void getClientsData() {
        task = new MyTask();
        task.execute();
    }

    private class MyTask extends AsyncTask<Void, Void, List<RecyclerClientDTO>> {


        @Override
        protected List<RecyclerClientDTO> doInBackground(Void... voids) {
            return dao.getAllClients();
        }

        @Override
        protected void onPostExecute(List<RecyclerClientDTO> recyclerClientDTOS) {
            adapter.setDtos(recyclerClientDTOS);
            adapter.notifyDataSetChanged();
        }
    }
}
