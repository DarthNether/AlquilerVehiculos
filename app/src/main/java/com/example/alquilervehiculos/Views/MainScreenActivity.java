package com.example.alquilervehiculos.Views;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.alquilervehiculos.R;
import com.example.alquilervehiculos.Views.Fragments.ViewVehiclesFragment;

public class MainScreenActivity extends AppCompatActivity implements ViewVehiclesFragment.OnFragmentInteractionListener {
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.dynamic_fragment_fram_layout, new  ViewVehiclesFragment());
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
