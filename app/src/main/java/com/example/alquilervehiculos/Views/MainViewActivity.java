package com.example.alquilervehiculos.Views;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.alquilervehiculos.R;
import com.example.alquilervehiculos.Views.Fragments.NewClientFragment;
import com.example.alquilervehiculos.Views.Fragments.NewVehicleFragment;
import com.example.alquilervehiculos.Views.Fragments.ViewClientsFragment;
import com.example.alquilervehiculos.Views.Fragments.ViewVehiclesFragment;

import java.util.Objects;

public class MainViewActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ViewVehiclesFragment.OnFragmentInteractionListener,
        ViewClientsFragment.OnFragmentInteractionListener,
        NewVehicleFragment.OnFragmentInteractionListener,
        NewClientFragment.OnFragmentInteractionListener {

    FragmentManager fragmentManager;
    NavigationView navigationView;

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener((View v) -> onFabClick());

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.dynamic_fragment_layout, new ViewVehiclesFragment(), "FRAGMENT_VEHICLES");
        fragmentTransaction.commit();

        navigationView.setCheckedItem(R.id.nav_vehicles);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Vehicles");
    }

    public void onFabClick() {
        Fragment currentFragment = this.fragmentManager.findFragmentById(R.id.dynamic_fragment_layout);

        if (currentFragment instanceof ViewClientsFragment) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.dynamic_fragment_layout, new NewClientFragment(), "FRAGMENT_NEW_CLIENT");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

            fab.hide();
        } else if (currentFragment instanceof ViewVehiclesFragment) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.dynamic_fragment_layout, new NewVehicleFragment(), "FRAGMENT_NEW_VEHICLE");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

            fab.hide();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        Fragment currentFragment = this.fragmentManager.findFragmentById(R.id.dynamic_fragment_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (currentFragment instanceof NewVehicleFragment) {
            super.onBackPressed();

            currentFragment = this.fragmentManager.findFragmentById(R.id.dynamic_fragment_layout);

            if (currentFragment instanceof ViewVehiclesFragment) {
                ((ViewVehiclesFragment) currentFragment).getVehiclesData();
            }

            fab.show();
        } else if (currentFragment instanceof NewClientFragment) {
            super.onBackPressed();

            currentFragment = this.fragmentManager.findFragmentById(R.id.dynamic_fragment_layout);

            if (currentFragment instanceof ViewClientsFragment) {
                ((ViewClientsFragment) currentFragment).getClientsData();
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment currentFragment = this.fragmentManager.findFragmentById(R.id.dynamic_fragment_layout);

        if (id == R.id.nav_vehicles) {
            if (!(currentFragment instanceof ViewVehiclesFragment)) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.dynamic_fragment_layout, new ViewVehiclesFragment(), "FRAGMENT_VEHICLES");
                fragmentTransaction.commit();

                Objects.requireNonNull(getSupportActionBar()).setTitle("Vehicles");
            }
        } else if (id == R.id.nav_clients) {
            if (!(currentFragment instanceof ViewClientsFragment)) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.dynamic_fragment_layout, new ViewClientsFragment(), "FRAGMENT_CLIENTS");
                fragmentTransaction.commit();

                Objects.requireNonNull(getSupportActionBar()).setTitle("Clients");
            }
        } else if (id == R.id.nav_settings) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
