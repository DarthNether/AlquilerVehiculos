package com.example.alquilervehiculos.Views.Fragments;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alquilervehiculos.Classes.Utils.ItemClickSupport;
import com.example.alquilervehiculos.DAO.ClientDAO;
import com.example.alquilervehiculos.DTO.RecyclerClientDTO;
import com.example.alquilervehiculos.R;
import com.example.alquilervehiculos.Views.Adapters.ClientsAdapter;
import com.example.alquilervehiculos.Views.MainViewActivity;

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
    String recentlyDeleted;

    private Drawable icon;
    private ColorDrawable background;

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback;

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

        icon = ContextCompat.getDrawable(getContext(),
                R.drawable.ic_delete_black_24dp);
        background = new ColorDrawable(Color.RED);
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

        this.configureItemTocuhHelper();
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerClients);

        ((MainViewActivity) getActivity()).changeFABIcon(R.drawable.ic_add_white_24dp);

        this.configureOnClickRecyclerView();

        return view;
    }

    private void configureItemTocuhHelper() {
        itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {


            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                int position = viewHolder.getAdapterPosition();
                RecyclerClientDTO clientDTO = adapter.getDtos().get(position);
                new RemoveClientTask().execute(clientDTO.getId());
                new GetClientsTask().execute();
                recentlyDeleted = clientDTO.getId();

                Snackbar snackbar = Snackbar.make(getView(), R.string.snackbar_deleted_client, Snackbar.LENGTH_LONG);
                snackbar.setAction(R.string.snackbar_undo, v -> undoDelete());
                snackbar.show();
            }

            private void undoDelete() {
                new RestoreClientTask().execute(recentlyDeleted);
                new GetClientsTask().execute();
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                View itemView = viewHolder.itemView;
                int backgroundCornerOffset = 20;

                if (dX < 0) { // Swiping to the left
                    background.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset,
                            itemView.getTop(), itemView.getRight(), itemView.getBottom());
                } else { // view is unSwiped
                    background.setBounds(0, 0, 0, 0);
                }
                background.draw(c);

                int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
                int iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
                int iconBottom = iconTop + icon.getIntrinsicHeight();

                if (dX < 0) { // Swiping to the left
                    int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
                    int iconRight = itemView.getRight() - iconMargin;
                    icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

                    background.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset,
                            itemView.getTop(), itemView.getRight(), itemView.getBottom());
                } else { // view is unSwiped
                    background.setBounds(0, 0, 0, 0);
                }

                background.draw(c);
                icon.draw(c);
            }
        };
    }

    private void configureOnClickRecyclerView() {
        ItemClickSupport.addTo(recyclerClients, R.layout.fragment_view_clients)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        RecyclerClientDTO clientDTO = adapter.getClient(position);
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.dynamic_fragment_layout, ClientDetailsFragment.newInstance(clientDTO.getId()));
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });
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

    public void getClientsData() {
        new GetClientsTask().execute();
    }

    private class GetClientsTask extends AsyncTask<Void, Void, List<RecyclerClientDTO>> {


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

    private class RemoveClientTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            dao.removeClient(strings[0]);
            return null;
        }
    }

    private class RestoreClientTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            dao.restoreClient((strings[0]));
            return null;
        }
    }
}
