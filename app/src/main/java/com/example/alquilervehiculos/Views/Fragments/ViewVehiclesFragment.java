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
import com.example.alquilervehiculos.DAO.VehicleDAO;
import com.example.alquilervehiculos.DTO.RecyclerVehicleDTO;
import com.example.alquilervehiculos.R;
import com.example.alquilervehiculos.Views.VehiclesAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewVehiclesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewVehiclesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewVehiclesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    RecyclerView recyclerVehicles;
    VehiclesAdapter adapter;
    String recentlyDeleted;

    private Drawable icon;
    private ColorDrawable background;

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {


        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            int position = viewHolder.getAdapterPosition();
            RecyclerVehicleDTO vehicleDTO = adapter.getDtos().get(position);
            new RemoveVehicleTask().execute(vehicleDTO.getId());
            new GetVehiclesTask().execute();
            recentlyDeleted = vehicleDTO.getId();

            Snackbar snackbar = Snackbar.make(getView(), R.string.snackbar_deleted_vehicle, Snackbar.LENGTH_LONG);
            snackbar.setAction(R.string.snackbar_undo, v -> undoDelete());
            snackbar.show();
        }

        private void undoDelete() {
            new RestoreVehicleTask().execute(recentlyDeleted);
            new GetVehiclesTask().execute();
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            View itemView = viewHolder.itemView;
            int backgroundCornerOffset = 20;

            if (dX > 0) { // Swiping to the right
                background.setBounds(itemView.getLeft(), itemView.getTop(),
                        itemView.getLeft() + ((int) dX) + backgroundCornerOffset,
                        itemView.getBottom());

            } else if (dX < 0) { // Swiping to the left
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

    VehicleDAO dao;
    GetVehiclesTask task;

    public ViewVehiclesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewVehiclesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewVehiclesFragment newInstance(String param1, String param2) {
        ViewVehiclesFragment fragment = new ViewVehiclesFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_vehicles, container, false);

        recyclerVehicles = view.findViewById(R.id.recViewVehicles);

        dao = new VehicleDAO(getContext());

        adapter = new VehiclesAdapter();
        getVehiclesData();
        recyclerVehicles.setAdapter(adapter);

        recyclerVehicles.setLayoutManager(new LinearLayoutManager(getContext()));

        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerVehicles);

        this.configureOnClickRecyclerView();

        return view;
    }

    private void configureOnClickRecyclerView() {
        ItemClickSupport.addTo(recyclerVehicles, R.layout.fragment_view_vehicles)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    RecyclerVehicleDTO vehicleDTO = adapter.getVehicle(position);

                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.dynamic_fragment_layout, new VehicleDetailsFragment());
                    transaction.addToBackStack(null);
                    transaction.commit();
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
        task.cancel(true);
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

    public void getVehiclesData() {
        task = new GetVehiclesTask();
        task.execute();
    }

    private class GetVehiclesTask extends AsyncTask<Void, Void, List<RecyclerVehicleDTO>> {

        @Override
        protected List<RecyclerVehicleDTO> doInBackground(Void... voids) {
            return dao.getAllVehicles();
        }

        @Override
        protected void onPostExecute(List<RecyclerVehicleDTO> recyclerVehicleDTOS) {
            adapter.setDtos(recyclerVehicleDTOS);
            adapter.notifyDataSetChanged();
        }
    }

    private class RemoveVehicleTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            dao.removeVehicle(strings[0]);
            return null;
        }
    }

    private class RestoreVehicleTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            dao.restoreVehicle((strings[0]));
            return null;
        }
    }
}
